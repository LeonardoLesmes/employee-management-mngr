package com.employee_management_mngr.auth.application.ports.input.dtos;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthResponse {
    private Integer id;
    private String name;
    private String role;
    private String token;
}
