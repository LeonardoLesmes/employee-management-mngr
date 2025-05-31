package com.employee_management_mngr.auth.application.exceptions;

public class AuthenticationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AuthenticationException(String message) {
        super(message);
    }
}
