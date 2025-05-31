package com.employee_management_mngr.employee.application.exceptions;

public class ErrorPostCreationEmployee extends RuntimeException {

    public ErrorPostCreationEmployee() {
        super("Error while post creating employee");
    }    
}
