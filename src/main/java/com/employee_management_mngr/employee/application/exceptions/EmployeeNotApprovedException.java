package com.employee_management_mngr.employee.application.exceptions;

public class EmployeeNotApprovedException extends RuntimeException {
    public EmployeeNotApprovedException(String message) {
        super(message);
    }
}
