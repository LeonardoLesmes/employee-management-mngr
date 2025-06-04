package com.employee_management_mngr.access.application.ports.output;

import java.util.List;
import java.util.Optional;

import com.employee_management_mngr.access.domain.System;

public interface SystemRepository {
    Optional<System> findById(Integer id);

    List<System> findAll();
}
