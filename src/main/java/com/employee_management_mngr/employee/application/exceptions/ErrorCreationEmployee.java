package com.employee_management_mngr.employee.application.exceptions;

public class ErrorCreationEmployee extends RuntimeException {
    public ErrorCreationEmployee(String message) {
        super(message);
    }

    public ErrorCreationEmployee(String message, Throwable cause) {
        super(message, cause);
    }
}
