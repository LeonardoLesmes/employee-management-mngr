package com.employee_management_mngr.auth.application.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterManagerRequest {
    private String name;
    private String email;
    private Integer roleId;
}
