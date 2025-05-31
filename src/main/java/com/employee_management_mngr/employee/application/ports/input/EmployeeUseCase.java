package com.employee_management_mngr.employee.application.ports.input;

import com.employee_management_mngr.employee.domain.Employee;

public interface EmployeeUseCase {
    Employee createEmployee(Employee employee);
    Employee findEmployeeById(Integer id);
}
