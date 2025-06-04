package com.employee_management_mngr.employee.application.orchestrators;

import com.employee_management_mngr.employee.application.ports.input.RoleUseCase;
import com.employee_management_mngr.employee.application.services.RoleService;
import com.employee_management_mngr.employee.domain.role.Role;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Component
@Transactional
@RequiredArgsConstructor
public class RoleOrchestrator implements RoleUseCase {

    private final RoleService roleService;

    @Override
    public Role findById(Integer id) {
        return roleService.findById(id);
    }

    @Override
    public List<Role> findAll() {
        return roleService.findAll();
    }

    @Override
    public Role save(Role role) {
        return roleService.save(role);
    }

    @Override
    public void deleteById(Integer id) {
        roleService.deleteById(id);
    }
}
