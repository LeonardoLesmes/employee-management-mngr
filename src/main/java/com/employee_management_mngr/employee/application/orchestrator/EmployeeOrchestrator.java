package com.employee_management_mngr.employee.application.orchestrator;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.employee_management_mngr.employee.application.services.EmployeeService;
import com.employee_management_mngr.employee.application.services.ThirdPartyIntegrationService;
import com.employee_management_mngr.employee.domain.Employee;

@Component
public class EmployeeOrchestrator {
    private final EmployeeService employeeService;
    private final ThirdPartyIntegrationService thirdPartyIntegrationService;

    public EmployeeOrchestrator(
            EmployeeService employeeService,
            ThirdPartyIntegrationService thirdPartyIntegrationService) {
        this.employeeService = employeeService;
        this.thirdPartyIntegrationService = thirdPartyIntegrationService;
    }

    @Transactional
    public Employee createEmployee(Employee employee) {
        Employee savedEmployee = employeeService.createEmployee(employee);
        thirdPartyIntegrationService.notifyEmployeeCreation(savedEmployee);

        return savedEmployee;
    }

    @Transactional
    public Employee findEmployeeById(Integer id) {
        return employeeService.findEmployeeById(id);
    }
}