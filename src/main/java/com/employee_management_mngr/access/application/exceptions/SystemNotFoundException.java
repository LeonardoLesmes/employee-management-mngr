package com.employee_management_mngr.access.application.exceptions;

public class SystemNotFoundException extends RuntimeException {
    public SystemNotFoundException(String message) {
        super(message);
    }
}
