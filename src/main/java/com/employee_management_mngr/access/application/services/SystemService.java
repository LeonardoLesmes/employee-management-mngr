package com.employee_management_mngr.access.application.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employee_management_mngr.access.application.exceptions.SystemNotFoundException;
import com.employee_management_mngr.access.application.mappers.RolePermissionMap;
import com.employee_management_mngr.access.application.ports.output.SystemRepository;
import com.employee_management_mngr.access.domain.System;

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

    public List<Integer> getSystemsAllowedToRole(Integer roleId) {
        return RolePermissionMap.getAllowedSystemIds(roleId);
    }
}
