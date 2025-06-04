package com.employee_management_mngr.computer.application.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employee_management_mngr.computer.application.exceptions.ComputerAssignmentException;
import com.employee_management_mngr.computer.application.ports.output.ComputerAssignmentRepository;
import com.employee_management_mngr.computer.application.ports.output.ComputerRepository;
import com.employee_management_mngr.computer.domain.Computer;
import com.employee_management_mngr.computer.domain.ComputerAssignment;
import com.employee_management_mngr.computer.domain.ComputerAssignmentStatus;
import com.employee_management_mngr.computer.domain.ComputerStatus;
import com.employee_management_mngr.employee.application.exceptions.EmployeeNotApprovedException;
import com.employee_management_mngr.employee.application.ports.input.EmployeeUseCase;
import com.employee_management_mngr.employee.domain.employee.Employee;
import com.employee_management_mngr.employee.domain.employee.EmployeeStatus;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ComputerAssignmentService {
    private final ComputerAssignmentRepository computerAssignmentRepository;
    private final ComputerRepository computerRepository;
    private final EmployeeUseCase employeeUseCase;

    public ComputerAssignment createAssignment(Integer employeeId, Integer computerId, Integer assignedById) {
        Employee employee = employeeUseCase.findEmployeeById(employeeId);

        if (employee.getStatus() != EmployeeStatus.APPROVED) {
            throw new EmployeeNotApprovedException(employeeId.toString());
        }

        Computer computer = computerRepository.findById(computerId)
                .orElseThrow(() -> new ComputerAssignmentException("Computer not found with id: " + computerId));

        if (computer.getStatus() != ComputerStatus.AVAILABLE) {
            throw new ComputerAssignmentException("Computer is not available for assignment");
        }

        computer.setStatus(ComputerStatus.IN_PROCESS);

        ComputerAssignment assignment = new ComputerAssignment();
        assignment.setEmployee(employee);
        assignment.setComputer(computer);
        assignment.setAssignedBy(assignedById);
        assignment.setStatus(ComputerAssignmentStatus.PENDING);

        return computerAssignmentRepository.save(assignment);
    }

    public ComputerAssignment updateAssignmentStatus(Integer assignmentId, ComputerAssignmentStatus status) {
        ComputerAssignment assignment = computerAssignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new ComputerAssignmentException("Assignment not found with id: " + assignmentId));

        assignment.setStatus(status);
        assignment.setResolutionDate(LocalDateTime.now());

        Computer computer = assignment.getComputer();
        if (status == ComputerAssignmentStatus.APPROVED) {
            assignment.setResolutionDate(LocalDateTime.now());
            computer.setStatus(ComputerStatus.ASSIGNED);
        } else if (status == ComputerAssignmentStatus.REJECTED || status == ComputerAssignmentStatus.CANCELED) {
            computer.setStatus(ComputerStatus.AVAILABLE);
        }
        computerRepository.save(computer);

        return computerAssignmentRepository.save(assignment);
    }

    public List<ComputerAssignment> findByEmployeeId(Integer employeeId) {
        Employee employee = employeeUseCase.findEmployeeById(employeeId);
        return computerAssignmentRepository.findByEmployee(employee);
    }

    public List<ComputerAssignment> findByAssignedById(Integer assignedById) {
        if (assignedById == null) {
            throw new ComputerAssignmentException("AssignedBy ID cannot be null");
        }
        return computerAssignmentRepository.findByAssignedBy(assignedById);
    }

    public List<Computer> findAvailableComputers() {
        return computerRepository.findAvailableComputers();
    }
}
