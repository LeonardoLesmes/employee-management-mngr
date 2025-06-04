package com.employee_management_mngr.system.application.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employee_management_mngr.system.application.ports.output.SystemRepository;
import com.employee_management_mngr.system.domain.System;
import com.employee_management_mngr.system.application.exceptions.SystemNotFoundException;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SystemService {
    private final SystemRepository systemRepository;

    public System findSystemById(Integer id) {
        return systemRepository.findById(id)
                .orElseThrow(() -> new SystemNotFoundException("System not found with id: " + id));
    }

    public List<System> findAll() {
        return systemRepository.findAll();
    }
}
