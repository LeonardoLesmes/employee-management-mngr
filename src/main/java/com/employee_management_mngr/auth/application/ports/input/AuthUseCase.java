package com.employee_management_mngr.auth.application.ports.input;

import com.employee_management_mngr.auth.application.ports.input.dtos.AuthRequest;
import com.employee_management_mngr.auth.application.ports.input.dtos.AuthResponse;
import com.employee_management_mngr.auth.application.ports.input.dtos.CreateManagerPasswordRequest;

public interface AuthUseCase {
    AuthResponse authenticateManager(AuthRequest request);   
    void createManagerPassword(CreateManagerPasswordRequest request);
}
