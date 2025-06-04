package com.employee_management_mngr.access.application.exceptions;

public class AlreadyExistAccessRequest extends RuntimeException {
    public AlreadyExistAccessRequest(String message) {
        super(message);
    }
}
