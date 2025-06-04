package com.employee_management_mngr.access.infrastructure.adapters.inputs;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.employee_management_mngr.access.application.ports.input.SystemUseCase;
import com.employee_management_mngr.access.infrastructure.adapters.inputs.dto.SystemDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/systems")
@RequiredArgsConstructor
public class SystemController {
    private final SystemUseCase systemUseCase;

    @GetMapping
    public ResponseEntity<List<SystemDTO>> getAllSystems() {
        List<SystemDTO> systems = systemUseCase.findAll().stream().map(SystemDTO::fromEntity).toList();
        return ResponseEntity.ok(systems);
    }

    @GetMapping("/available/{roleId}")
    public ResponseEntity<List<Integer>> getAvailableSystemsForRole(@PathVariable Integer roleId) {
        List<Integer> systemIds = systemUseCase.getSystemsAllowedToRole(roleId);
        return ResponseEntity.status(systemIds.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK).body(systemIds);
    }
}
