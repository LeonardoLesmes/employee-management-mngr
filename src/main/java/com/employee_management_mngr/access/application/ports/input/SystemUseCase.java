package com.employee_management_mngr.access.application.ports.input;

import java.util.List;

import com.employee_management_mngr.access.domain.System;

public interface SystemUseCase {
    System findSystemById(Integer id);

    List<System> findAll();
}
