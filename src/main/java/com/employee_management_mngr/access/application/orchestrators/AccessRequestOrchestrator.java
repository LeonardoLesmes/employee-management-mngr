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
}
