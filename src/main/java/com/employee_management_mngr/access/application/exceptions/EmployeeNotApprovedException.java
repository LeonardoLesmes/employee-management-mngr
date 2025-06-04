package com.employee_management_mngr.access.application.exceptions;

public class EmployeeNotApprovedException extends RuntimeException {
    public EmployeeNotApprovedException(String message) {
        super(message);
    }
}
