package com.employee_management_mngr.system.infrastructure.inputs;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.employee_management_mngr.system.application.ports.input.SystemUseCase;
import com.employee_management_mngr.system.infrastructure.inputs.dto.SystemDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/systems")
@RequiredArgsConstructor
public class SystemController {
    private final SystemUseCase systemUseCase;

    @GetMapping
    public ResponseEntity<List<SystemDTO>> getAllSystems() {
        List<SystemDTO> systems = systemUseCase.findAll()
            .stream()
            .map(SystemDTO::fromEntity)
            .toList();
        return ResponseEntity.ok(systems);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SystemDTO> getSystemById(@PathVariable Integer id) {
        SystemDTO system = SystemDTO.fromEntity(systemUseCase.findSystemById(id));
        return ResponseEntity.ok(system);
    }
}
