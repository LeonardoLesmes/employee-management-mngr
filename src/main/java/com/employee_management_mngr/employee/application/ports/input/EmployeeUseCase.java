package com.employee_management_mngr.employee.application.ports.input;

import com.employee_management_mngr.employee.domain.employee.Employee;
import com.employee_management_mngr.employee.domain.employee.EmployeeStatus;

public interface EmployeeUseCase {
    Employee createEmployee(Employee employee);
    Employee findEmployeeById(Integer id);
    Employee findEmployeeByEmail(String email);
    void updateEmployeeStatus(Integer employeeId, EmployeeStatus newStatus);
}
