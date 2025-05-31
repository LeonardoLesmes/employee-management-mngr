package com.employee_management_mngr.access.application.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                
                AccessRequest accessRequest = new AccessRequest();
                accessRequest.setEmployee(employee);
                accessRequest.setSystem(system);
                accessRequest.setStatus(AccessRequestStatus.PENDING);
                accessRequest.setRequestDate(LocalDateTime.now());
                accessRequest.setAssignedBy(assignedBy);
                
                return accessRequestRepository.save(accessRequest);
            })
            .toList();
    }
}
