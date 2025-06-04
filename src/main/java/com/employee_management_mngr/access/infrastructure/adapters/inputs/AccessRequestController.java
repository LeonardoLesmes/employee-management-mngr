package com.employee_management_mngr.access.infrastructure.adapters.inputs;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.employee_management_mngr.access.application.exceptions.AccessRequestCreationException;
import com.employee_management_mngr.access.application.exceptions.AlreadyExistAccessRequest;
import com.employee_management_mngr.access.application.exceptions.SystemNotFoundException;
import com.employee_management_mngr.access.application.exceptions.UnauthorizedSystemAccessException;
import com.employee_management_mngr.access.application.ports.input.AccessRequestUseCase;
import com.employee_management_mngr.access.domain.AccessRequest;
import com.employee_management_mngr.access.infrastructure.adapters.inputs.dto.AccessRequestDTO;
import com.employee_management_mngr.access.infrastructure.adapters.inputs.dto.CreateAccessRequestDTO;
import com.employee_management_mngr.access.infrastructure.adapters.inputs.dto.UpdateAccessRequestStatusDTO;
import com.employee_management_mngr.employee.application.exceptions.EmployeeNotApprovedException;
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
    public ResponseEntity<List<AccessRequestDTO>> getAccessRequestsByEmployeeId(@PathVariable Integer employeeId) {
        List<AccessRequest> requests = accessRequestUseCase.findByEmployeeId(employeeId);
        List<AccessRequestDTO> dtos = requests.stream().map(AccessRequestDTO::fromEntity).toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/assigned-by/{assignedById}")
    public ResponseEntity<List<AccessRequestDTO>> getAccessRequestsByAssignedById(@PathVariable Integer assignedById) {
        List<AccessRequest> requests = accessRequestUseCase.findByAssignedById(assignedById);
        List<AccessRequestDTO> dtos = requests.stream().map(AccessRequestDTO::fromEntity).toList();
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<AccessRequestDTO> updateAccessRequestStatus(@PathVariable Integer id,
            @RequestBody UpdateAccessRequestStatusDTO request) {
        accessRequestUseCase.updateAccessRequestStatus(id, request.getStatus());
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(EmployeeNotApprovedException.class)
    public ResponseEntity<String> handleEmployeeNotApproved(EmployeeNotApprovedException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
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

    @ExceptionHandler(AlreadyExistAccessRequest.class)
    public ResponseEntity<String> handleAlreadyExistAccessRequest(AlreadyExistAccessRequest e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(UnauthorizedSystemAccessException.class)
    public ResponseEntity<String> handleUnauthorizedSystemAccess(UnauthorizedSystemAccessException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
