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
import org.springframework.web.bind.annotation.RestController;

import com.employee_management_mngr.employee.application.exceptions.EmployeeNotFoundException;
import com.employee_management_mngr.employee.application.exceptions.ErrorCreationEmployee;
import com.employee_management_mngr.employee.application.exceptions.ErrorPostCreationEmployee;
import com.employee_management_mngr.employee.application.exceptions.InvalidEmployeeRequest;
import com.employee_management_mngr.employee.application.ports.input.EmployeeUseCase;
import com.employee_management_mngr.employee.domain.employee.Employee;
import com.employee_management_mngr.employee.domain.employee.EmployeeStatus;
import com.employee_management_mngr.employee.infrastructure.adapters.inputs.dto.CreateEmployeeDto;
import com.employee_management_mngr.employee.infrastructure.adapters.inputs.dto.UpdateEmployeeStatusDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeUseCase employeeUseCase;

    @PostMapping
    public ResponseEntity<Void> createEmployee(@RequestBody CreateEmployeeDto employee) {
        employeeUseCase.createEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable Integer id) {
        return ResponseEntity.ok(employeeUseCase.findEmployeeById(id));
    }

    @GetMapping("/assigned-by/{assignedById}")
    public ResponseEntity<List<Employee>> getEmployeesByAssignedBy(@PathVariable Integer assignedById) {
        List<Employee> employees = employeeUseCase.findEmployeesByAssignedBy(assignedById);
        return ResponseEntity.ok(employees);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Employee> updateEmployeeStatus(@PathVariable Integer id,
            @RequestBody UpdateEmployeeStatusDto updateEmployeeStatusDto) {
        employeeUseCase.updateEmployeeStatus(id, EmployeeStatus.valueOf(updateEmployeeStatusDto.getStatus().name()));
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
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
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<String> handleEmployeeNotFound(EmployeeNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
