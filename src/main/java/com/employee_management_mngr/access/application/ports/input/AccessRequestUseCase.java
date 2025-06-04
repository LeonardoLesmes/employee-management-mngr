package com.employee_management_mngr.access.application.ports.input;

import java.util.List;
import com.employee_management_mngr.access.domain.AccessRequest;
import com.employee_management_mngr.access.domain.AccessRequestStatus;

public interface AccessRequestUseCase {
    List<AccessRequest> createAccessRequests(Integer employeeId, List<Integer> systemIds, Integer assignedById);
    List<AccessRequest> findByEmployeeId(Integer employeeId);
    List<AccessRequest> findByAssignedById(Integer assignedById);
    AccessRequest updateAccessRequestStatus(Integer requestId, AccessRequestStatus newStatus);
}
