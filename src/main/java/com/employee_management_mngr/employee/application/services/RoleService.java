package com.employee_management_mngr.employee.application.services;

import com.employee_management_mngr.employee.application.ports.input.RoleUseCase;
import com.employee_management_mngr.employee.application.ports.output.RoleRepository;
import com.employee_management_mngr.employee.domain.role.Role;
import com.employee_management_mngr.employee.infrastructure.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService implements RoleUseCase {
    
    private final RoleRepository roleRepository;

    @Override
    public Role findById(Integer id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void deleteById(Integer id) {
        findById(id);
        roleRepository.deleteById(id);
    }
}
