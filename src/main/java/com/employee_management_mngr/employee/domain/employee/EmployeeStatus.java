package com.employee_management_mngr.employee.domain.employee;

import lombok.Getter;

@Getter
public enum EmployeeStatus {
    PENDING("pending"),
    APPROVED("approved"),
    REJECTED("rejected");

    private final String value;

    EmployeeStatus(String value) {
        this.value = value;
    }
}
