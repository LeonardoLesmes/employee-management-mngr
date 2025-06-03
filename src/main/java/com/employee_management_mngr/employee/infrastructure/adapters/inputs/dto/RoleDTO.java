package com.employee_management_mngr.employee.infrastructure.adapters.inputs.dto;

import com.employee_management_mngr.employee.domain.role.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {
    private Integer id;
    private String type;
    private String description;
    private boolean canLogin;

    public static RoleDTO fromEntity(Role role) {
        RoleDTO dto = new RoleDTO();
        dto.setId(role.getId());
        dto.setType(role.getType().getName());
        dto.setDescription(role.getDescription());
        dto.setCanLogin(role.getType().isCanLogin());
        return dto;
    }
}
