package com.employee_management_mngr.employee.infrastructure.adapters.inputs;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.employee_management_mngr.employee.application.exceptions.EmployeeNotFoundException;
import com.employee_management_mngr.employee.application.exceptions.ErrorCreationEmployee;
import com.employee_management_mngr.employee.application.exceptions.ErrorPostCreationEmployee;
import com.employee_management_mngr.employee.application.exceptions.InvalidEmployeeRequest;
import com.employee_management_mngr.employee.application.orchestrator.EmployeeOrchestrator;
import com.employee_management_mngr.employee.domain.employee.Employee;
import com.employee_management_mngr.employee.domain.employee.EmployeeStatus;
import com.employee_management_mngr.employee.infrastructure.adapters.inputs.dto.CreateEmployeeDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeOrchestrator employeeOrchestrator;    @PostMapping
    public ResponseEntity<Void> createEmployee(@RequestBody CreateEmployeeDto employee) {
        employeeOrchestrator.createEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable Integer id) {
        return ResponseEntity.ok(employeeOrchestrator.findEmployeeById(id));
    }
      @GetMapping("/assigned-by/{assignedById}")
    public ResponseEntity<List<Employee>> getEmployeesByAssignedBy(@PathVariable Integer assignedById) {
        List<Employee> employees = employeeOrchestrator.findEmployeesByAssignedBy(assignedById);
        return ResponseEntity.ok(employees);
    }
        
    @GetMapping("/range")
    public ResponseEntity<List<Employee>> getEmployeesByIdRange(
        @RequestParam Integer startId,
        @RequestParam Integer endId,
        @RequestParam(required = false) Integer assignedById
    ) {
        List<Employee> employees;
        
        if (assignedById == null) {
            employees = employeeOrchestrator.findEmployeesByIdRange(startId, endId);
        } else {
            employees = employeeOrchestrator.findEmployeesByIdRangeAndAssignedBy(startId, endId, assignedById);
        }
        
        return ResponseEntity.ok(employees);
    }

    @PutMapping("/{id}/status/{status}")
    public ResponseEntity<Employee> updateEmployeeStatus(
            @PathVariable Integer id, 
            @PathVariable EmployeeStatus status) {
        employeeOrchestrator.updateEmployeeStatus(id, status);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(ErrorCreationEmployee.class)
    public ResponseEntity<String> handleErrorCreationEmployee(ErrorCreationEmployee e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @ExceptionHandler(ErrorPostCreationEmployee.class)
    public ResponseEntity<String> handleErrorPostCreationEmployee(ErrorPostCreationEmployee e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();            
    }

    @ExceptionHandler(InvalidEmployeeRequest.class)
    public ResponseEntity<String> handleInvalidEmployeeRequest(InvalidEmployeeRequest e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(e.getMessage());
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<String> handleEmployeeNotFound(EmployeeNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
