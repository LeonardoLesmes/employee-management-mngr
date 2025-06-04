package com.employee_management_mngr.employee.application.ports.input;

import java.util.List;

import com.employee_management_mngr.employee.domain.employee.Employee;
import com.employee_management_mngr.employee.domain.employee.EmployeeStatus;
import com.employee_management_mngr.employee.infrastructure.adapters.inputs.dto.CreateEmployeeDto;

public interface EmployeeUseCase {
    void createEmployee(CreateEmployeeDto employee);

    Employee findEmployeeById(Integer id);

    Employee findEmployeeByEmail(String email);

    void updateEmployeeStatus(Integer employeeId, EmployeeStatus newStatus);

    List<Employee> findEmployeesByAssignedBy(Integer assignedById);
}
