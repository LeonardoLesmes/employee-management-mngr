package com.employee_management_mngr.auth.application.ports.input;

import com.employee_management_mngr.auth.domain.Manager;

public interface ManagerUseCase {
    Manager findManagerByEmail(String email);
}
