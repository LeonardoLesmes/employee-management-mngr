package com.employee_management_mngr.employee.application.services;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employee_management_mngr.employee.application.exceptions.EmployeeNotFoundException;
import com.employee_management_mngr.employee.application.ports.input.EmployeeUseCase;
import com.employee_management_mngr.employee.application.ports.output.EmployeeRepository;
import com.employee_management_mngr.employee.domain.Employee;

@Service
@Transactional
public class EmployeeService implements EmployeeUseCase {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee createEmployee(Employee employee) {
                try {
            // Validaciones básicas
            if (employee.getName() == null || employee.getName().trim().isEmpty()) {
                throw new IllegalArgumentException("Employee name cannot be empty");
            }
            if (employee.getEmail() == null || employee.getEmail().trim().isEmpty()) {
                throw new IllegalArgumentException("Employee email cannot be empty");
            }
            
            // Establecer valores por defecto
            if (employee.getStatus() == null) {
                employee.setStatus("pending");
            }
            if (employee.getCreatedAt() == null) {
                employee.setCreatedAt(LocalDateTime.now());
            }
            
            return employeeRepository.save(employee);
        } catch (Exception e) {
            throw new RuntimeException("Error creating employee: " + e.getMessage(), e);
        }
    }

    @Override
    public Employee findEmployeeById(Integer id) {
        return employeeRepository.findById(id)
            .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));
    }
}
