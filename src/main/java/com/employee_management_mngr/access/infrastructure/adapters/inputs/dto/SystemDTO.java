package com.employee_management_mngr.access.infrastructure.adapters.inputs.dto;

import com.employee_management_mngr.access.domain.System;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SystemDTO {
    private Integer id;
    private String name;
    private String description;

    public static SystemDTO fromEntity(System system) {
        SystemDTO dto = new SystemDTO();
        dto.setId(system.getId());
        dto.setName(system.getName());
        dto.setDescription(system.getDescription());
        return dto;
    }
}
