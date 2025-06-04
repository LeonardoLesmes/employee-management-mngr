package com.employee_management_mngr.employee.infrastructure.adapters.inputs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEmployeeDto {
    private String name;
    private String email;
    private String department;
    private Integer roleId;
    private Integer assignedBy;
}
