package com.employee_management_mngr.auth.application.ports.input;

public interface TokenValidationUseCase {
    /**
     * Validates if the given JWT token is valid
     * 
     * @param token The JWT token to validate
     * @return true if the token is valid, false otherwise
     */
    boolean validateToken(String token);
}
