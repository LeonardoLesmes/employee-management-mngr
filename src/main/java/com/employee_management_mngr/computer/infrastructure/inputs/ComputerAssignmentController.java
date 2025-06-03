package com.employee_management_mngr.computer.infrastructure.inputs;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.employee_management_mngr.computer.application.exceptions.ComputerAssignmentException;
import com.employee_management_mngr.computer.application.ports.input.ComputerAssignmentUseCase;
import com.employee_management_mngr.computer.domain.Computer;
import com.employee_management_mngr.computer.domain.ComputerAssignment;
import com.employee_management_mngr.computer.domain.ComputerAssignmentStatus;
import com.employee_management_mngr.computer.infrastructure.inputs.dto.ComputerAssignmentDTO;
import com.employee_management_mngr.computer.infrastructure.inputs.dto.ComputerDTO;
import com.employee_management_mngr.computer.infrastructure.inputs.dto.CreateComputerAssignmentDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/computer-assignments")
@RequiredArgsConstructor
public class ComputerAssignmentController {
    private final ComputerAssignmentUseCase computerAssignmentUseCase;

    @PostMapping
    public ResponseEntity<ComputerAssignmentDTO> createAssignment(@RequestBody CreateComputerAssignmentDTO request) {
        ComputerAssignment assignment = computerAssignmentUseCase.createAssignment(
            request.getEmployeeId(),
            request.getComputerId(),
            request.getAssignedById()
        );
        return ResponseEntity.ok(ComputerAssignmentDTO.fromEntity(assignment));
    }

    @PutMapping("/{id}/status/{status}")
    public ResponseEntity<ComputerAssignmentDTO> updateAssignmentStatus(
        @PathVariable Integer id,
        @PathVariable ComputerAssignmentStatus status
    ) {
        ComputerAssignment assignment = computerAssignmentUseCase.updateAssignmentStatus(id, status);
        return ResponseEntity.ok(ComputerAssignmentDTO.fromEntity(assignment));
    }
    
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<ComputerAssignmentDTO>> getAssignmentsByEmployee(@PathVariable Integer employeeId) {
        List<ComputerAssignmentDTO> assignments = computerAssignmentUseCase.findByEmployeeId(employeeId)
            .stream()
            .map(ComputerAssignmentDTO::fromEntity)
            .toList();
        return ResponseEntity.ok(assignments);
    }
    
    @GetMapping("/assigned-by/{assignedById}")
    public ResponseEntity<List<ComputerAssignmentDTO>> getAssignmentsByAssignedBy(@PathVariable Integer assignedById) {
        List<ComputerAssignmentDTO> assignments = computerAssignmentUseCase.findByAssignedById(assignedById)
            .stream()
            .map(ComputerAssignmentDTO::fromEntity)
            .toList();
        return ResponseEntity.ok(assignments);
    }

    @GetMapping("/active")
    public ResponseEntity<List<ComputerAssignmentDTO>> getActiveAssignments() {
        List<ComputerAssignmentDTO> assignments = computerAssignmentUseCase.findActiveAssignments()
            .stream()
            .map(ComputerAssignmentDTO::fromEntity)
            .toList();
        return ResponseEntity.ok(assignments);
    }    @GetMapping("/available")
    public ResponseEntity<List<ComputerDTO>> getAvailableComputers() {
        List<Computer> computers = computerAssignmentUseCase.findAvailableComputers();
        List<ComputerDTO> dtos = computers.stream()
                .map(ComputerDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(dtos);
    }
      @GetMapping("/range")
    public ResponseEntity<List<ComputerAssignmentDTO>> getAssignmentsByIdRange(
        @RequestParam Integer startId,
        @RequestParam Integer endId,
        @RequestParam(required = false) Integer assignedById
    ) {
        List<ComputerAssignment> assignments;
        
        if (assignedById == null) {
            assignments = computerAssignmentUseCase.findByIdRange(startId, endId);
        } else {
            assignments = computerAssignmentUseCase.findByIdRangeAndAssignedBy(startId, endId, assignedById);
        }
        
        List<ComputerAssignmentDTO> assignmentDTOs = assignments.stream()
            .map(ComputerAssignmentDTO::fromEntity)
            .toList();
            
        return ResponseEntity.ok(assignmentDTOs);
    }
    
    @ExceptionHandler(ComputerAssignmentException.class)
    public ResponseEntity<String> handleComputerAssignmentException(ComputerAssignmentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(e.getMessage());
    }
}
