package com.employee_management_mngr.access.application.exceptions;

public class AccessRequestCreationException extends RuntimeException {
    public AccessRequestCreationException(String message) {
        super(message);
    }

    public AccessRequestCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
