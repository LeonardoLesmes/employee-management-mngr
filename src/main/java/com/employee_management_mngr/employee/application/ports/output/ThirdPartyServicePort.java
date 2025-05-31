package com.employee_management_mngr.employee.application.ports.output;

import com.employee_management_mngr.employee.domain.employee.Employee;

public interface ThirdPartyServicePort {
    void notifyNewEmployee(Employee employee);
}
