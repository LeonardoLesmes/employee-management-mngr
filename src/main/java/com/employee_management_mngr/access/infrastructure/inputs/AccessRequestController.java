package com.employee_management_mngr.access.infrastructure.inputs;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.employee_management_mngr.access.application.exceptions.AccessRequestCreationException;
import com.employee_management_mngr.access.application.ports.input.AccessRequestUseCase;
import com.employee_management_mngr.access.domain.AccessRequest;
import com.employee_management_mngr.access.infrastructure.inputs.dto.AccessRequestDTO;
import com.employee_management_mngr.access.infrastructure.inputs.dto.CreateAccessRequestDTO;
import com.employee_management_mngr.access.infrastructure.inputs.dto.UpdateAccessRequestStatusDTO;
import com.employee_management_mngr.system.application.exceptions.SystemNotFoundException;
import com.employee_management_mngr.employee.application.exceptions.EmployeeNotFoundException;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/access-requests")
@RequiredArgsConstructor
public class AccessRequestController {
    private final AccessRequestUseCase accessRequestUseCase;

    @PostMapping
    public ResponseEntity<Void> createAccessRequests(@RequestBody CreateAccessRequestDTO request) {
        accessRequestUseCase.createAccessRequests(request.getEmployeeId(), request.getSystemIds(),
                request.getAssignedById());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<AccessRequestDTO>> getAccessRequestsByEmployeeId(@PathVariable Integer employeeId,
            @RequestParam(required = false) Integer assignedById) {
        List<AccessRequest> requests = accessRequestUseCase.findByEmployeeIdAndAssignedBy(employeeId, assignedById);
        List<AccessRequestDTO> dtos = requests.stream().map(AccessRequestDTO::fromEntity).toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/assigned-by/{assignedById}")
    public ResponseEntity<List<AccessRequestDTO>> getAccessRequestsByAssignedById(@PathVariable Integer assignedById) {
        List<AccessRequest> requests = accessRequestUseCase.findByAssignedById(assignedById);
        List<AccessRequestDTO> dtos = requests.stream().map(AccessRequestDTO::fromEntity).toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/range")
    public ResponseEntity<List<AccessRequestDTO>> getAccessRequestsByIdRange(@RequestParam Integer startId,
            @RequestParam Integer endId, @RequestParam(required = false) Integer assignedById) {
        List<AccessRequest> requests;

        if (assignedById == null) {
            requests = accessRequestUseCase.findByIdRange(startId, endId);
        } else {
            requests = accessRequestUseCase.findByIdRangeAndAssignedBy(startId, endId, assignedById);
        }

        List<AccessRequestDTO> dtos = requests.stream().map(AccessRequestDTO::fromEntity).toList();

        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<AccessRequestDTO> updateAccessRequestStatus(@PathVariable Integer id,
            @RequestBody UpdateAccessRequestStatusDTO request) {
        AccessRequest updatedRequest = accessRequestUseCase.updateAccessRequestStatus(id, request.getStatus());
        return ResponseEntity.ok(AccessRequestDTO.fromEntity(updatedRequest));
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<String> handleEmployeeNotFound(EmployeeNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found");
    }

    @ExceptionHandler(SystemNotFoundException.class)
    public ResponseEntity<String> handleSystemNotFound(SystemNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("System not found");
    }

    @ExceptionHandler(AccessRequestCreationException.class)
    public ResponseEntity<String> handleAccessRequestCreation(AccessRequestCreationException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
