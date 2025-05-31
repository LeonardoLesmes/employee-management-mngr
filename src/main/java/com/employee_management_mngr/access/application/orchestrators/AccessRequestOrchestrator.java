package com.employee_management_mngr.access.application.orchestrators;

import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.employee_management_mngr.access.application.ports.input.AccessRequestUseCase;
import com.employee_management_mngr.access.application.services.AccessRequestService;
import com.employee_management_mngr.access.domain.AccessRequest;

import lombok.RequiredArgsConstructor;

@Component
@Transactional
@RequiredArgsConstructor
public class AccessRequestOrchestrator implements AccessRequestUseCase {
    private final AccessRequestService accessRequestService;

    @Override
    public List<AccessRequest> createAccessRequests(Integer employeeId, List<Integer> systemIds, Integer assignedById) {
        return accessRequestService.createAccessRequests(employeeId, systemIds, assignedById);
    }
}
