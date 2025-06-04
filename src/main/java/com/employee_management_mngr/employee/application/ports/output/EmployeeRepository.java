package com.employee_management_mngr.employee.application.ports.output;

import java.util.List;
import java.util.Optional;

import com.employee_management_mngr.employee.domain.employee.Employee;

public interface EmployeeRepository {
    Employee save(Employee employee);

    Optional<Employee> findById(Integer id);

    Optional<Employee> findByEmail(String email);

    List<Employee> findByAssignedBy(Integer assignedBy);

    List<Employee> findByIdRange(Integer startId, Integer endId);

    List<Employee> findByIdRangeAndAssignedBy(Integer startId, Integer endId, Integer assignedBy);
}
