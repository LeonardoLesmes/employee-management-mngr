package com.employee_management_mngr.employee.application.orchestrator;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.employee_management_mngr.employee.application.exceptions.ErrorPostCreationEmployee;
import com.employee_management_mngr.employee.application.exceptions.InvalidEmployeeRequest;
import com.employee_management_mngr.employee.application.ports.input.EmployeeUseCase;
import com.employee_management_mngr.employee.application.services.EmployeeService;
import com.employee_management_mngr.employee.application.services.ThirdPartyIntegrationService;
import com.employee_management_mngr.employee.domain.employee.Employee;
import com.employee_management_mngr.auth.application.ports.output.AuthRepository;
import com.employee_management_mngr.auth.domain.Credentials;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@Transactional
@RequiredArgsConstructor
public class EmployeeOrchestrator implements EmployeeUseCase {
    
    private final EmployeeService employeeService;
    private final ThirdPartyIntegrationService thirdPartyIntegrationService;
    private final AuthRepository authRepository;
    
    private static final Logger logger = LoggerFactory.getLogger(EmployeeOrchestrator.class);

    @Override
    public Employee createEmployee(Employee employee) {
        employee.setStatus("pending");
        Employee savedEmployee = employeeService.createEmployee(employee);
        setupEmployeePostCreation(savedEmployee);
        return savedEmployee;
    }

    private void setupEmployeePostCreation(Employee employee) throws ErrorPostCreationEmployee {
        try {
            createEmployeeCredentials(employee);
            notifyThirdPartyServices(employee);
        } catch (Exception e) {
            logger.error("Error during post-creation setup for employee", e);
            throw new ErrorPostCreationEmployee();
        }
    }

    private void createEmployeeCredentials(Employee employee) {
        Credentials credentials = new Credentials();
        credentials.setEmployee(employee);
        credentials.setPasswordHash("");
        credentials.setIsActive(true);
        authRepository.save(credentials);
    }

    private void notifyThirdPartyServices(Employee employee) {
        thirdPartyIntegrationService.notifyEmployeeCreation(employee);
    }

    @Override
    public Employee findEmployeeById(Integer id) {
        return employeeService.findEmployeeById(id);
    }

    @Override
    public Employee findEmployeeByEmail(String email) {
        return employeeService.findEmployeeByEmail(email);
    }
}