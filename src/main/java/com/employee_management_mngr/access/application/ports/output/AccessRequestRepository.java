package com.employee_management_mngr.access.application.ports.output;

import java.util.List;
import java.util.Optional;
import com.employee_management_mngr.access.domain.AccessRequest;
import com.employee_management_mngr.access.domain.System;
import com.employee_management_mngr.employee.domain.employee.Employee;

public interface AccessRequestRepository {
    AccessRequest save(AccessRequest accessRequest);
    Optional<AccessRequest> findById(Integer id);
    Optional<AccessRequest> findByEmployeeAndSystem(Employee employee, System system);
    List<AccessRequest> findByEmployeeId(Integer employeeId);
    List<AccessRequest> findByAssignedById(Integer assignedById);
}
