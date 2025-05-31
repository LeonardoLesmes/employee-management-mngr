package com.employee_management_mngr.employee.application.services;

import org.springframework.stereotype.Service;

import com.employee_management_mngr.employee.application.ports.output.ThirdPartyServicePort;
import com.employee_management_mngr.employee.domain.employee.Employee;

@Service
public class ThirdPartyIntegrationService {
    private final ThirdPartyServicePort thirdPartyServicePort;

    public ThirdPartyIntegrationService(ThirdPartyServicePort thirdPartyServicePort) {
        this.thirdPartyServicePort = thirdPartyServicePort;
    }

    public void notifyEmployeeCreation(Employee employee) {
        thirdPartyServicePort.notifyNewEmployee(employee);
    }
}
