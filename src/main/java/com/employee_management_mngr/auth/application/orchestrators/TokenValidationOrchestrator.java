package com.employee_management_mngr.auth.application.orchestrators;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.employee_management_mngr.auth.application.ports.input.TokenValidationUseCase;
import com.employee_management_mngr.auth.application.services.JwtService;

import lombok.RequiredArgsConstructor;

@Component
@Transactional
@RequiredArgsConstructor
public class TokenValidationOrchestrator implements TokenValidationUseCase {

    private final JwtService jwtService;

    @Override
    public boolean validateToken(String token) {
        if (token == null || token.isEmpty()) {
            return false;
        }

        // Remove "Bearer " prefix if present
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        return jwtService.isValidToken(token);
    }
}
