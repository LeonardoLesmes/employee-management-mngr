package com.employee_management_mngr.auth.application.ports.input.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateManagerPasswordRequest {
    private String email;
    private String password;
}
