package com.employee_management_mngr.employee.application.exceptions;

public class InvalidEmployeeRequest extends RuntimeException {
    public InvalidEmployeeRequest(String message) {
        super(message);
    }
}
