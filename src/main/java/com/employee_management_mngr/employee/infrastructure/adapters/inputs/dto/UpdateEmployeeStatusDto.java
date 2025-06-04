package com.employee_management_mngr.employee.infrastructure.adapters.inputs.dto;

import com.employee_management_mngr.employee.domain.employee.EmployeeStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateEmployeeStatusDto {
    private EmployeeStatus status;
}
