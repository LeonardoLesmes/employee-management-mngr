package com.employee_management_mngr.auth.application.ports.output;

import java.util.Optional;

import com.employee_management_mngr.auth.domain.ManagerRole;

public interface ManagerRoleRepository {
    Optional<ManagerRole> findById(Integer id);
}
