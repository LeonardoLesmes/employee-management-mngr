package com.employee_management_mngr.employee.application.ports.output;

import java.util.Optional;

import com.employee_management_mngr.employee.domain.Employee;

public interface EmployeeRepository {
    Employee save(Employee employee);
    Optional<Employee> findById(Integer id);
}
