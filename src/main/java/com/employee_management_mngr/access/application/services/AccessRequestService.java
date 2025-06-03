package com.employee_management_mngr.access.application.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employee_management_mngr.access.application.exceptions.AccessRequestCreationException;
import com.employee_management_mngr.access.application.ports.output.AccessRequestRepository;
import com.employee_management_mngr.access.domain.AccessRequest;
import com.employee_management_mngr.access.domain.AccessRequestStatus;
import com.employee_management_mngr.employee.application.ports.input.EmployeeUseCase;
import com.employee_management_mngr.employee.domain.employee.Employee;
import com.employee_management_mngr.system.application.ports.input.SystemUseCase;
import com.employee_management_mngr.system.domain.System;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AccessRequestService {
    private final AccessRequestRepository accessRequestRepository;
    private final EmployeeUseCase employeeUseCase;
    private final SystemUseCase systemUseCase;

    public List<AccessRequest> createAccessRequests(Integer employeeId, List<Integer> systemIds, Integer assignedById) {
        Employee employee = employeeUseCase.findEmployeeById(employeeId);
        Employee assignedBy = employeeUseCase.findEmployeeById(assignedById);
        
        return systemIds.stream()
            .map(systemId -> {
                System system = systemUseCase.findSystemById(systemId);
                
                Optional<AccessRequest> existingRequest = accessRequestRepository.findByEmployeeAndSystem(employee, system);
                
                if (existingRequest.isPresent()) {
                    AccessRequestStatus status = existingRequest.get().getStatus();
                    if (status != AccessRequestStatus.CANCELLED && status != AccessRequestStatus.REJECTED) {
                        throw new AccessRequestCreationException(
                            "Access request already exists for employee " + employeeId + 
                            " and system " + systemId + " with status " + status);
                    }
                }
                
                AccessRequest accessRequest = new AccessRequest();
                accessRequest.setEmployee(employee);
                accessRequest.setSystem(system);
                accessRequest.setStatus(AccessRequestStatus.PENDING);
                accessRequest.setRequestDate(LocalDateTime.now());
                accessRequest.setAssignedBy(assignedBy);
                
                return accessRequestRepository.save(accessRequest);
            })
            .toList();
    }    public List<AccessRequest> findByEmployeeIdAndAssignedBy(Integer employeeId, Integer assignedById) {
        List<AccessRequest> requests = findByEmployeeId(employeeId);    
        if (assignedById == null) {
            return requests;
        }
        return requests.stream()
            .filter(request -> request.getAssignedBy().getId().equals(assignedById))
            .toList();        
    }

    public List<AccessRequest> findByEmployeeId(Integer employeeId) {
        employeeUseCase.findEmployeeById(employeeId);
        return accessRequestRepository.findByEmployeeId(employeeId);
    }
    
    public List<AccessRequest> findByAssignedById(Integer assignedById) {
        if (assignedById == null) {
            throw new AccessRequestCreationException("AssignedBy ID cannot be null");
        }
        
        // Verificamos que el empleado existe
        employeeUseCase.findEmployeeById(assignedById);
        
        return accessRequestRepository.findByAssignedById(assignedById);
    }    public AccessRequest updateAccessRequestStatus(Integer requestId, AccessRequestStatus newStatus) {
        AccessRequest accessRequest = accessRequestRepository.findById(requestId)
            .orElseThrow(() -> new AccessRequestCreationException("Access request not found with id: " + requestId));
        
        if (newStatus == null) {
            throw new AccessRequestCreationException("New status cannot be null");
        }
        
        accessRequest.setStatus(newStatus);
        if (newStatus != AccessRequestStatus.PENDING) {
            accessRequest.setResolutionDate(LocalDateTime.now());
        }
        
        return accessRequestRepository.save(accessRequest);
    }
    
    public List<AccessRequest> findByIdRange(Integer startId, Integer endId) {
        if (startId == null || endId == null) {
            throw new AccessRequestCreationException("Start ID and End ID cannot be null");
        }
        
        if (startId > endId) {
            throw new AccessRequestCreationException("Start ID cannot be greater than End ID");
        }
        
        return accessRequestRepository.findByIdRange(startId, endId);
    }
    
    public List<AccessRequest> findByIdRangeAndAssignedBy(Integer startId, Integer endId, Integer assignedById) {
        if (startId == null || endId == null) {
            throw new AccessRequestCreationException("Start ID and End ID cannot be null");
        }
        
        if (startId > endId) {
            throw new AccessRequestCreationException("Start ID cannot be greater than End ID");
        }
        
        if (assignedById == null) {
            return findByIdRange(startId, endId);
        }
        
        Employee assignedBy = employeeUseCase.findEmployeeById(assignedById);
        return accessRequestRepository.findByIdRangeAndAssignedBy(startId, endId, assignedBy);
    }
}
