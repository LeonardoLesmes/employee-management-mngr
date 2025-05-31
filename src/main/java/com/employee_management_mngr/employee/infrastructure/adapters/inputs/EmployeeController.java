package com.employee_management_mngr.employee.infrastructure.adapters.inputs;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee_management_mngr.employee.application.exceptions.EmployeeNotFoundException;
import com.employee_management_mngr.employee.application.exceptions.ErrorCreationEmployee;
import com.employee_management_mngr.employee.application.exceptions.ErrorPostCreationEmployee;
import com.employee_management_mngr.employee.application.exceptions.InvalidEmployeeRequest;
import com.employee_management_mngr.employee.application.orchestrator.EmployeeOrchestrator;
import com.employee_management_mngr.employee.domain.employee.Employee;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeOrchestrator employeeOrchestrator;

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(employeeOrchestrator.createEmployee(employee));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable Integer id) {
        return ResponseEntity.ok(employeeOrchestrator.findEmployeeById(id));
    }

    @ExceptionHandler(ErrorCreationEmployee.class)
    public ResponseEntity<String> handleErrorCreationEmployee(ErrorCreationEmployee e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(e.getMessage());
    }

    @ExceptionHandler(ErrorPostCreationEmployee.class)
    public ResponseEntity<String> handleErrorPostCreationEmployee(ErrorPostCreationEmployee e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(e.getMessage());
    }

    @ExceptionHandler(InvalidEmployeeRequest.class)
    public ResponseEntity<String> handleInvalidEmployeeRequest(InvalidEmployeeRequest e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(e.getMessage());
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<String> handleEmployeeNotFound(EmployeeNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(e.getMessage());
    }
}
