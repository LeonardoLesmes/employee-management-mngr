package com.employee_management_mngr.access.application.orchestrators;

import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.employee_management_mngr.access.application.ports.input.SystemUseCase;
import com.employee_management_mngr.access.application.services.SystemService;
import com.employee_management_mngr.access.domain.System;

import lombok.RequiredArgsConstructor;

@Component
@Transactional
@RequiredArgsConstructor
public class SystemOrchestrator implements SystemUseCase {
    private final SystemService systemService;

    @Override
    public System findSystemById(Integer id) {
        return systemService.findSystemById(id);
    }

    @Override
    public List<System> findAll() {
        return systemService.findAll();
    }
}
