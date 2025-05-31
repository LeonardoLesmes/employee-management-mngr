package com.employee_management_mngr.auth.application.ports.input;

import com.employee_management_mngr.auth.application.dtos.AuthRequest;
import com.employee_management_mngr.auth.application.dtos.AuthResponse;

public interface AuthUseCase {
    AuthResponse authenticate(AuthRequest request);
    void createPassword(AuthRequest request);
}
