package com.employee_management_mngr.employee.application.services;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employee_management_mngr.employee.application.exceptions.EmployeeNotFoundException;
import com.employee_management_mngr.employee.application.exceptions.ErrorCreationEmployee;
import com.employee_management_mngr.employee.application.exceptions.InvalidEmployeeRequest;
import com.employee_management_mngr.employee.application.exceptions.RequiredFieldMissingException;
import com.employee_management_mngr.employee.application.ports.output.EmployeeRepository;
import com.employee_management_mngr.employee.domain.employee.Employee;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
  
    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    public Employee createEmployee(Employee employee) {
        try {
            validateRequiredFields(employee);
            setDefaultValues(employee);        
            return employeeRepository.save(employee);
        } catch (RequiredFieldMissingException e) {
            throw new InvalidEmployeeRequest(e.getMessage());
        }
        catch (Exception e) {
            logger.error("Error creating employee: {}", e.getMessage(), e);
            throw new ErrorCreationEmployee(e.getMessage());
        }
    }

    private void validateRequiredFields(Employee employee) throws RequiredFieldMissingException {
        if (employee.getName() == null || employee.getName().trim().isEmpty()) {
            throw new RequiredFieldMissingException("name");
        }
        if (employee.getEmail() == null || employee.getEmail().trim().isEmpty()) {
            throw new RequiredFieldMissingException("email");
        }
        if (employee.getRole() == null) {
            throw new RequiredFieldMissingException("role");
        }
        if (employee.getDepartment() == null || employee.getDepartment().trim().isEmpty()) {
            throw new RequiredFieldMissingException("department");
        }
        if (employee.getAssignedBy() == null) {
            throw new RequiredFieldMissingException("assignedBy");
        }
    }

    private void setDefaultValues(Employee employee) {
        if (employee.getCreatedAt() == null) {
            employee.setCreatedAt(LocalDateTime.now());
        }
        if (employee.getRoleAssignedAt() == null) {
            employee.setRoleAssignedAt(LocalDateTime.now());
        }
    }
    

    public Employee findEmployeeById(Integer id) {
        return employeeRepository.findById(id)
            .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));
    }

    public Employee findEmployeeByEmail(String email) {
        return employeeRepository.findByEmail(email)
            .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with email: " + email));
    }
}
