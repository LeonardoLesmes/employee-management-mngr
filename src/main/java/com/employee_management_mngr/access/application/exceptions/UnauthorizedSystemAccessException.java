package com.employee_management_mngr.access.application.exceptions;

public class UnauthorizedSystemAccessException extends RuntimeException {
    public UnauthorizedSystemAccessException(String message) {
        super(message);
    }
}
