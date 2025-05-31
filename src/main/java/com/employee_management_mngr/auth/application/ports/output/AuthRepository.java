package com.employee_management_mngr.auth.application.ports.output;

import java.util.Optional;

import com.employee_management_mngr.auth.domain.Credentials;

public interface AuthRepository {
    Optional<Credentials> findByEmployeeEmail(String email);
    Optional<Credentials> save(Credentials credentials); 
}
