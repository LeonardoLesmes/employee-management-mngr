package com.employee_management_mngr.auth.application.ports.input;

import com.employee_management_mngr.auth.application.dtos.AuthRequest;
import com.employee_management_mngr.auth.application.dtos.AuthResponse;
import com.employee_management_mngr.auth.application.dtos.CreateManagerPasswordRequest;

public interface AuthUseCase {
    AuthResponse authenticateManager(AuthRequest request);   
    void createManagerPassword(CreateManagerPasswordRequest request);
}
