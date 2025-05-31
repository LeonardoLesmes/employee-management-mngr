package com.employee_management_mngr.access.application.ports.input;

import java.util.List;
import com.employee_management_mngr.access.domain.AccessRequest;

public interface AccessRequestUseCase {
    List<AccessRequest> createAccessRequests(Integer employeeId, List<Integer> systemIds, Integer assignedById);
    List<AccessRequest> findByEmployeeId(Integer employeeId);    
    List<AccessRequest> findByEmployeeIdAndAssignedBy(Integer employeeId, Integer assignedById);
}
