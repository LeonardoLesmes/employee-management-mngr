package com.employee_management_mngr.computer.infrastructure.adapters.inputs;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.employee_management_mngr.computer.application.exceptions.ComputerAssignmentException;
import com.employee_management_mngr.computer.application.ports.input.ComputerAssignmentUseCase;
import com.employee_management_mngr.computer.domain.Computer;
import com.employee_management_mngr.computer.domain.ComputerAssignment;
import com.employee_management_mngr.computer.domain.ComputerAssignmentStatus;
import com.employee_management_mngr.computer.infrastructure.adapters.inputs.dto.ComputerAssignmentDTO;
import com.employee_management_mngr.computer.infrastructure.adapters.inputs.dto.ComputerDTO;
import com.employee_management_mngr.computer.infrastructure.adapters.inputs.dto.CreateComputerAssignmentDTO;
import com.employee_management_mngr.computer.infrastructure.adapters.inputs.dto.UpdateComputerAssignmentStatusDto;
import com.employee_management_mngr.employee.application.exceptions.EmployeeNotApprovedException;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/computer-assignments")
@RequiredArgsConstructor
public class ComputerAssignmentController {
    private final ComputerAssignmentUseCase computerAssignmentUseCase;

    @PostMapping
    public ResponseEntity<ComputerAssignmentDTO> createAssignment(@RequestBody CreateComputerAssignmentDTO request) {
        ComputerAssignment assignment = computerAssignmentUseCase.createAssignment(request.getEmployeeId(),
                request.getComputerId(), request.getAssignedById());
        return ResponseEntity.ok(ComputerAssignmentDTO.fromEntity(assignment));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateAssignmentStatus(@PathVariable Integer id,
            @RequestBody UpdateComputerAssignmentStatusDto status) {
        computerAssignmentUseCase.updateAssignmentStatus(id, ComputerAssignmentStatus.valueOf(status.getStatus().name()));
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<ComputerAssignmentDTO> getAssignmentsByEmployee(@PathVariable Integer employeeId) {
        List<ComputerAssignmentDTO> assignments = computerAssignmentUseCase.findByEmployeeId(employeeId).stream()
                .map(ComputerAssignmentDTO::fromEntity).toList();
        if (assignments.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.ok(assignments.getFirst());
        }
    }

    @GetMapping("/assigned-by/{assignedById}")
    public ResponseEntity<List<ComputerAssignmentDTO>> getAssignmentsByAssignedBy(@PathVariable Integer assignedById) {
        List<ComputerAssignmentDTO> assignments = computerAssignmentUseCase.findByAssignedById(assignedById).stream()
                .map(ComputerAssignmentDTO::fromEntity).toList();
        return ResponseEntity.ok(assignments);
    }

    @GetMapping("/available")
    public ResponseEntity<List<ComputerDTO>> getAvailableComputers() {
        List<Computer> computers = computerAssignmentUseCase.findAvailableComputers();
        List<ComputerDTO> dtos = computers.stream().map(ComputerDTO::fromEntity).toList();
        return ResponseEntity.ok(dtos);
    }

    @ExceptionHandler(ComputerAssignmentException.class)
    public ResponseEntity<String> handleComputerAssignmentException(ComputerAssignmentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(EmployeeNotApprovedException.class)
    public ResponseEntity<String> handleEmployeeNotApproved(EmployeeNotApprovedException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
}
