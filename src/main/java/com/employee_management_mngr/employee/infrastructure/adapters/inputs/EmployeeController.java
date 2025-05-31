package com.employee_management_mngr.employee.infrastructure.adapters.inputs;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee_management_mngr.employee.application.orchestrator.EmployeeOrchestrator;
import com.employee_management_mngr.employee.domain.Employee;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeOrchestrator employeeOrchestrator;

    public EmployeeController(EmployeeOrchestrator employeeOrchestrator) {
        this.employeeOrchestrator = employeeOrchestrator;
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        return ResponseEntity.ok(employeeOrchestrator.createEmployee(employee));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable Integer id) {
        return ResponseEntity.ok(employeeOrchestrator.findEmployeeById(id));
    }
}
