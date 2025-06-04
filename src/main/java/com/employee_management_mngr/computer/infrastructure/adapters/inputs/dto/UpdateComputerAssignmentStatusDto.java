package com.employee_management_mngr.computer.infrastructure.adapters.inputs.dto;

import com.employee_management_mngr.computer.domain.ComputerAssignmentStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateComputerAssignmentStatusDto {
    ComputerAssignmentStatus status;
}
