package com.employee_management_mngr.access.application.orchestrators;

import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.employee_management_mngr.access.application.exceptions.AccessRequestCreationException;
import com.employee_management_mngr.access.application.ports.input.AccessRequestUseCase;
import com.employee_management_mngr.access.application.services.AccessRequestService;
import com.employee_management_mngr.access.domain.AccessRequest;
import com.employee_management_mngr.access.domain.AccessRequestStatus;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@Transactional
@RequiredArgsConstructor
public class AccessRequestOrchestrator implements AccessRequestUseCase {
    private final AccessRequestService accessRequestService;

    private static final Logger logger = LoggerFactory.getLogger(AccessRequestOrchestrator.class);

    @Override
    public List<AccessRequest> createAccessRequests(Integer employeeId, List<Integer> systemIds, Integer assignedById) {
        return accessRequestService.createAccessRequests(employeeId, systemIds, assignedById);
    }

    @Override
    public List<AccessRequest> findByEmployeeId(Integer employeeId) {
        return accessRequestService.findByEmployeeId(employeeId);
    }

    @Override
    public List<AccessRequest> findByEmployeeIdAndAssignedBy(Integer employeeId, Integer assignedById) {
        return accessRequestService.findByEmployeeIdAndAssignedBy(employeeId, assignedById);
    }

    @Override
    public List<AccessRequest> findByAssignedById(Integer assignedById) {
        try {
            return accessRequestService.findByAssignedById(assignedById);
        } catch (Exception e) {
            logger.error("Error finding access requests by assignedById {}: {}", assignedById, e.getMessage(), e);
            throw new AccessRequestCreationException("Error finding access requests by assignedById: " + e.getMessage(),
                    e);
        }
    }

    @Override
    public AccessRequest updateAccessRequestStatus(Integer requestId, AccessRequestStatus newStatus) {
        return accessRequestService.updateAccessRequestStatus(requestId, newStatus);
    }

    @Override
    public List<AccessRequest> findByIdRange(Integer startId, Integer endId) {
        try {
            return accessRequestService.findByIdRange(startId, endId);
        } catch (Exception e) {
            logger.error("Error finding access requests by ID range {} to {}: {}", startId, endId, e.getMessage(), e);
            throw new AccessRequestCreationException("Error finding access requests by ID range: " + e.getMessage(), e);
        }
    }

    @Override
    public List<AccessRequest> findByIdRangeAndAssignedBy(Integer startId, Integer endId, Integer assignedById) {
        try {
            return accessRequestService.findByIdRangeAndAssignedBy(startId, endId, assignedById);
        } catch (Exception e) {
            logger.error("Error finding access requests by ID range {} to {} and assignedBy ID {}: {}", startId, endId,
                    assignedById, e.getMessage(), e);
            throw new AccessRequestCreationException(
                    "Error finding access requests by ID range and assignedBy: " + e.getMessage(), e);
        }
    }
}
