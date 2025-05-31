package com.employee_management_mngr.auth.application.orchestrators;

import org.springframework.stereotype.Component;

import com.employee_management_mngr.auth.application.dtos.AuthRequest;
import com.employee_management_mngr.auth.application.dtos.AuthResponse;
import com.employee_management_mngr.auth.application.exceptions.AuthenticationException;
import com.employee_management_mngr.auth.application.ports.input.AuthUseCase;
import com.employee_management_mngr.auth.application.services.CredentialsService;
import com.employee_management_mngr.auth.application.services.JwtService;
import com.employee_management_mngr.employee.application.ports.input.EmployeeUseCase;
import com.employee_management_mngr.employee.domain.employee.Employee;
import com.employee_management_mngr.employee.domain.role.Role;

import jakarta.transaction.Transactional;

@Component
@Transactional
public class AuthOrchestrator implements AuthUseCase {

    private final CredentialsService credentialsService;
    private final EmployeeUseCase employeeUseCase;
    private final JwtService jwtService;

    public AuthOrchestrator(
        CredentialsService credentialsService,
        EmployeeUseCase employeeUseCase,
        JwtService jwtService
    ) {
        this.credentialsService = credentialsService;
        this.employeeUseCase = employeeUseCase;
        this.jwtService = jwtService;
    }

    @Override
    public AuthResponse authenticate(AuthRequest request) {
        Employee employee = this.employeeUseCase.findEmployeeByEmail(request.getEmail());
        Role role = employee.getRole();
        if (!employee.getRole().getType().isCanLogin()) {
            throw new AuthenticationException("User with role " + role.getType().getName() + " cannot login.");
        }        
        credentialsService.isValidCredentials(request.getEmail(), request.getPassword());

        String token = jwtService.generateToken(employee.getId(), role.getType().getName());

        return AuthResponse.builder()
            .id(employee.getId())
            .name(employee.getName())
            .role(employee.getRole().getType().getName())
            .token(token)
            .build();        
    }

    @Override
    public void createPassword(AuthRequest request) {
        credentialsService.createPassword(request);        
    }
}
