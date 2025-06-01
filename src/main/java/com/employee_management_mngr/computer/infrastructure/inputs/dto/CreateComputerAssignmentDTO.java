package com.employee_management_mngr.computer.infrastructure.inputs.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateComputerAssignmentDTO {
    private Integer employeeId;
    private Integer computerId;
    private Integer assignedById;
}
