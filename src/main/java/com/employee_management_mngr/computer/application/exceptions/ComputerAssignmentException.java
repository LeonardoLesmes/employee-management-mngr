package com.employee_management_mngr.computer.application.exceptions;

public class ComputerAssignmentException extends RuntimeException {
    
    public ComputerAssignmentException(String message) {
        super(message);
    }
    
    public ComputerAssignmentException(String message, Throwable cause) {
        super(message, cause);
    }
}
