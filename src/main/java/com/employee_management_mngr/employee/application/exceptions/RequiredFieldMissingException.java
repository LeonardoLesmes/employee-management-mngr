package com.employee_management_mngr.employee.application.exceptions;

public class RequiredFieldMissingException extends RuntimeException {
    public RequiredFieldMissingException(String fieldName) {
        super("Required field is missing or empty: " + fieldName);
    }
}
