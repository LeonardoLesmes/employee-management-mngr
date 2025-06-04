package com.employee_management_mngr.auth.application.ports.input;

public interface TokenValidationUseCase {
    boolean validateToken(String token);
}
