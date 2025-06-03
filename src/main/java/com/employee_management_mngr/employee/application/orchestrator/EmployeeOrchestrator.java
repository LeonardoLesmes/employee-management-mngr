package com.employee_management_mngr.employee.application.orchestrator;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.employee_management_mngr.employee.application.exceptions.ErrorPostCreationEmployee;
import com.employee_management_mngr.employee.application.exceptions.InvalidEmployeeRequest;
import com.employee_management_mngr.employee.application.exceptions.ErrorCreationEmployee;
import com.employee_management_mngr.employee.application.ports.input.EmployeeUseCase;
import com.employee_management_mngr.employee.application.services.EmployeeService;
import com.employee_management_mngr.employee.application.services.ThirdPartyIntegrationService;
import com.employee_management_mngr.employee.domain.employee.Employee;
import com.employee_management_mngr.employee.domain.employee.EmployeeStatus;
import com.employee_management_mngr.employee.domain.role.Role;
import com.employee_management_mngr.employee.infrastructure.adapters.inputs.dto.CreateEmployeeDto;
import com.employee_management_mngr.auth.application.ports.output.AuthRepository;
import com.employee_management_mngr.auth.domain.Credentials;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@Transactional
@RequiredArgsConstructor
public class EmployeeOrchestrator implements EmployeeUseCase {
      private final EmployeeService employeeService;
    private final ThirdPartyIntegrationService thirdPartyIntegrationService;
    private final AuthRepository authRepository;
    private final com.employee_management_mngr.employee.application.services.RoleService roleService;
    
    private static final Logger logger = LoggerFactory.getLogger(EmployeeOrchestrator.class);
    
    @Override
    public void createEmployee(CreateEmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setName(employeeDto.getName());
        employee.setEmail(employeeDto.getEmail());
        employee.setDepartment(employeeDto.getDepartment());
        employee.setStatus(EmployeeStatus.PENDING);
        
        if (employeeDto.getRoleId() == null) {
            throw new InvalidEmployeeRequest("Role ID must be provided for employee creation");
        }
        
        Role role = roleService.findById(employeeDto.getRoleId());

        if (role.getType().isCanLogin()) {
            throw new InvalidEmployeeRequest("Cannot create employees with administrator roles");
        }
        
        employee.setRole(role);
        
        
        if (employeeDto.getAssignedBy() != null) {
            employee.setAssignedBy(employeeDto.getAssignedBy());
        }
    
        employee.setRoleAssignedAt(java.time.LocalDateTime.now());
        
        Employee savedEmployee = employeeService.createEmployee(employee);
        setupEmployeePostCreation(savedEmployee);
    }    private void setupEmployeePostCreation(Employee employee) {
        try {
            createEmployeeCredentials(employee);
            notifyThirdPartyServices(employee);
        } catch (Exception e) {
            logger.error("Error during post-creation setup for employee ID {}: {}", 
                        employee.getId(), e.getMessage(), e);
            throw new ErrorPostCreationEmployee("Failed to complete setup for employee ID " + employee.getId(), e);
        }
    }

    private void createEmployeeCredentials(Employee employee) {
        Credentials credentials = new Credentials();
        credentials.setEmployee(employee);
        credentials.setPasswordHash("");
        credentials.setIsActive(true);
        authRepository.save(credentials);
    }

    private void notifyThirdPartyServices(Employee employee) {
        thirdPartyIntegrationService.notifyEmployeeCreation(employee);
    }

    @Override
    public Employee findEmployeeById(Integer id) {
        return employeeService.findEmployeeById(id);
    }

    @Override
    public Employee findEmployeeByEmail(String email) {
        return employeeService.findEmployeeByEmail(email);
    }    @Override
    public void updateEmployeeStatus(Integer employeeId, EmployeeStatus newStatus) {
        try {
            employeeService.updateEmployeeStatus(employeeId, newStatus);
        } catch (Exception e) {
            logger.error("Error updating status to {} for employee ID {}: {}", 
                        newStatus, employeeId, e.getMessage(), e);
            throw new ErrorCreationEmployee("Error updating employee status: " + e.getMessage(), e);
        }
    }
      @Override
    public List<Employee> findEmployeesByAssignedBy(Integer assignedById) {
        try {
            return employeeService.findEmployeesByAssignedBy(assignedById);
        } catch (Exception e) {
            logger.error("Error finding employees assigned by ID {}: {}", 
                        assignedById, e.getMessage(), e);
            throw new ErrorCreationEmployee("Error finding employees by assignedBy ID " + assignedById + ": " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<Employee> findEmployeesByIdRange(Integer startId, Integer endId) {
        try {
            return employeeService.findEmployeesByIdRange(startId, endId);
        } catch (Exception e) {
            logger.error("Error finding employees by ID range {} to {}: {}", 
                        startId, endId, e.getMessage(), e);
            throw new ErrorCreationEmployee("Error finding employees by ID range: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<Employee> findEmployeesByIdRangeAndAssignedBy(Integer startId, Integer endId, Integer assignedById) {
        try {
            return employeeService.findEmployeesByIdRangeAndAssignedBy(startId, endId, assignedById);
        } catch (Exception e) {
            logger.error("Error finding employees by ID range {} to {} and assignedBy ID {}: {}", 
                        startId, endId, assignedById, e.getMessage(), e);
            throw new ErrorCreationEmployee("Error finding employees by ID range and assignedBy: " + e.getMessage(), e);
        }
    }
}