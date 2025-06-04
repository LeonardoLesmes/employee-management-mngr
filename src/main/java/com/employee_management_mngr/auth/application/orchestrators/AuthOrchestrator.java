package com.employee_management_mngr.auth.application.orchestrators;

import org.springframework.stereotype.Component;

import com.employee_management_mngr.auth.application.exceptions.AuthenticationException;
import com.employee_management_mngr.auth.application.ports.input.AuthUseCase;
import com.employee_management_mngr.auth.application.ports.input.ManagerUseCase;
import com.employee_management_mngr.auth.application.ports.input.dtos.AuthRequest;
import com.employee_management_mngr.auth.application.ports.input.dtos.AuthResponse;
import com.employee_management_mngr.auth.application.ports.input.dtos.CreateManagerPasswordRequest;
import com.employee_management_mngr.auth.application.services.CredentialsService;
import com.employee_management_mngr.auth.application.services.JwtService;
import com.employee_management_mngr.auth.domain.Manager;
import com.employee_management_mngr.auth.domain.ManagerRole;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Component
@Transactional
@AllArgsConstructor
public class AuthOrchestrator implements AuthUseCase {

    private final CredentialsService credentialsService;
    private final ManagerUseCase managerUseCase;
    private final JwtService jwtService;

    @Override
    public AuthResponse authenticateManager(AuthRequest request) {
        Manager manager = this.managerUseCase.findManagerByEmail(request.getEmail());
        ManagerRole role = manager.getRole();

        credentialsService.isValidCredentials(request.getEmail(), request.getPassword());

        String token = jwtService.generateToken(manager.getId(), role.getName());

        return AuthResponse.builder().id(manager.getId()).name(manager.getName()).role(manager.getRole().getName())
                .token(token).build();
    }

    @Override
    public void createManagerPassword(CreateManagerPasswordRequest request) {
        Manager manager = this.managerUseCase.findManagerByEmail(request.getEmail());
        if (!Boolean.TRUE.equals(manager.getIsActive())) {
            throw new AuthenticationException("El manager no está activo");
        }
        AuthRequest authRequest = new AuthRequest();
        authRequest.setEmail(request.getEmail());
        authRequest.setPassword(request.getPassword());
        credentialsService.createPassword(authRequest);
    }
}
