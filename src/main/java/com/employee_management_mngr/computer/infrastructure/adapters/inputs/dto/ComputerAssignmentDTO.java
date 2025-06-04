package com.employee_management_mngr.computer.infrastructure.adapters.inputs.dto;

import java.time.LocalDateTime;

import com.employee_management_mngr.computer.domain.ComputerAssignment;
import com.employee_management_mngr.computer.domain.ComputerAssignmentStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComputerAssignmentDTO {
    private Integer id;
    private Integer employeeId;
    private String employeeName;
    private Integer computerId;
    private String computerModel;
    private ComputerAssignmentStatus status;
    private LocalDateTime requestDate;
    private LocalDateTime resolutionDate;
    private LocalDateTime assignmentDate;
    private LocalDateTime returnDate;
    private Integer assignedById;
    private String assignedByName;

    public static ComputerAssignmentDTO fromEntity(ComputerAssignment entity) {
        ComputerAssignmentDTO dto = new ComputerAssignmentDTO();
        dto.setId(entity.getId());
        dto.setEmployeeId(entity.getEmployee().getId());
        dto.setEmployeeName(entity.getEmployee().getName());
        dto.setComputerId(entity.getComputer().getId());
        dto.setComputerModel(entity.getComputer().getModel());
        dto.setStatus(entity.getStatus());
        dto.setRequestDate(entity.getRequestDate());
        dto.setResolutionDate(entity.getResolutionDate());
        dto.setAssignedById(entity.getAssignedBy());
        return dto;
    }
}
