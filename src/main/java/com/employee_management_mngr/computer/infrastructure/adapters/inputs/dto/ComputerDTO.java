package com.employee_management_mngr.computer.infrastructure.adapters.inputs.dto;

import com.employee_management_mngr.computer.domain.Computer;
import com.employee_management_mngr.computer.domain.ComputerStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComputerDTO {
    private Integer id;
    private String model;
    private String serialNumber;
    private ComputerStatus status;
    private String specs;

    public static ComputerDTO fromEntity(Computer computer) {
        ComputerDTO dto = new ComputerDTO();
        dto.setId(computer.getId());
        dto.setModel(computer.getModel());
        dto.setSerialNumber(computer.getSerialNumber());
        dto.setStatus(computer.getStatus());
        dto.setSpecs(computer.getSpecs());
        return dto;
    }
}
