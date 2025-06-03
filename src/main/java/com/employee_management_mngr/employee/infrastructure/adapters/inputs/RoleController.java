package com.employee_management_mngr.employee.infrastructure.adapters.inputs;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee_management_mngr.employee.application.orchestrators.RoleOrchestrator;
import com.employee_management_mngr.employee.domain.role.Role;
import com.employee_management_mngr.employee.infrastructure.adapters.inputs.dto.RoleDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {
    
    private final RoleOrchestrator roleOrchestrator;

    @GetMapping
    public ResponseEntity<List<RoleDTO>> getAllRoles() {
        List<RoleDTO> roles = roleOrchestrator.findAll()
                .stream()
                .filter(role -> !role.getType().isCanLogin())
                .map(RoleDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> getRoleById(@PathVariable Integer id) {
        Role role = roleOrchestrator.findById(id);
        return ResponseEntity.ok(RoleDTO.fromEntity(role));
    }
}
