package com.employee_management_mngr.auth.application.ports.output;

import java.util.Optional;

import com.employee_management_mngr.auth.domain.ManagerCredentials;

public interface AuthRepository {
    Optional<ManagerCredentials> findByManagerEmail(String email);

    Optional<ManagerCredentials> save(ManagerCredentials credentials);
}
