package com.employee_management_mngr.auth.application.ports.output;

import java.util.Optional;

import com.employee_management_mngr.auth.domain.Manager;

public interface ManagerRepository {
    Optional<Manager> findByEmail(String email);
    
    Manager save(Manager manager);
}
