package com.employee_management_mngr.access.infrastructure.inputs;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.employee_management_mngr.access.application.exceptions.AccessRequestCreationException;
import com.employee_management_mngr.access.application.ports.input.AccessRequestUseCase;
import com.employee_management_mngr.access.infrastructure.inputs.dto.CreateAccessRequestDTO;
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
        accessRequestUseCase.createAccessRequests(
            request.getEmployeeId(),
            request.getSystemIds(),
            request.getAssignedById()
        );
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<String> handleEmployeeNotFound(EmployeeNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("Employee not found");
    }

    @ExceptionHandler(SystemNotFoundException.class)
    public ResponseEntity<String> handleSystemNotFound(SystemNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("System not found");
    }

    @ExceptionHandler(AccessRequestCreationException.class)
    public ResponseEntity<String> handleAccessRequestCreation(AccessRequestCreationException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
