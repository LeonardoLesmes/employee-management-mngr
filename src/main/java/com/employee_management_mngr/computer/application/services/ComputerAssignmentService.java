package com.employee_management_mngr.computer.application.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employee_management_mngr.computer.application.exceptions.ComputerAssignmentException;
import com.employee_management_mngr.computer.application.ports.output.ComputerAssignmentRepository;
import com.employee_management_mngr.computer.application.ports.output.ComputerRepository;
import com.employee_management_mngr.computer.domain.Computer;
import com.employee_management_mngr.computer.domain.ComputerAssignment;
import com.employee_management_mngr.computer.domain.ComputerAssignmentStatus;
import com.employee_management_mngr.computer.domain.ComputerStatus;
import com.employee_management_mngr.employee.application.ports.input.EmployeeUseCase;
import com.employee_management_mngr.employee.domain.employee.Employee;

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
        Employee assignedBy = employeeUseCase.findEmployeeById(assignedById);
        Computer computer = computerRepository.findById(computerId)
                .orElseThrow(() -> new ComputerAssignmentException("Computer not found with id: " + computerId));

        if (computer.getStatus() != ComputerStatus.AVAILABLE) {
            throw new ComputerAssignmentException("Computer is not available for assignment");
        }

        ComputerAssignment assignment = new ComputerAssignment();
        assignment.setEmployee(employee);
        assignment.setComputer(computer);
        assignment.setAssignedBy(assignedBy);
        assignment.setStatus(ComputerAssignmentStatus.PENDING);
        assignment.setRequestDate(LocalDateTime.now());

        return computerAssignmentRepository.save(assignment);
    }

    public ComputerAssignment updateAssignmentStatus(Integer assignmentId, ComputerAssignmentStatus status) {
        ComputerAssignment assignment = computerAssignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new ComputerAssignmentException("Assignment not found with id: " + assignmentId));

        assignment.setStatus(status);
        assignment.setResolutionDate(LocalDateTime.now());

        if (status == ComputerAssignmentStatus.APPROVED) {
            assignment.setAssignmentDate(LocalDateTime.now());
            Computer computer = assignment.getComputer();
            computer.setStatus(ComputerStatus.ASSIGNED);
            computerRepository.save(computer);
        }

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
        Employee assignedBy = employeeUseCase.findEmployeeById(assignedById);
        return computerAssignmentRepository.findByAssignedBy(assignedBy);
    }

    public List<ComputerAssignment> findActiveAssignments() {
        return computerRepository.findAll().stream()
                .filter(computer -> computer.getStatus() == ComputerStatus.ASSIGNED)
                .map(computerAssignmentRepository::findActiveAssignmentByComputer)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    public List<Computer> findAvailableComputers() {
        return computerRepository.findAvailableComputers();
    }

    public List<ComputerAssignment> findByIdRange(Integer startId, Integer endId) {
        return findByIdRangeAndAssignedBy(startId, endId, null);
    }

    public List<ComputerAssignment> findByIdRangeAndAssignedBy(Integer startId, Integer endId, Integer assignedById) {
        if (startId == null || endId == null) {
            throw new ComputerAssignmentException("Start ID and End ID cannot be null");
        }

        if (startId > endId) {
            throw new ComputerAssignmentException("Start ID cannot be greater than End ID");
        }

        if (assignedById == null) {
            return computerAssignmentRepository.findByIdRange(startId, endId);
        } else {
            Employee assignedBy = employeeUseCase.findEmployeeById(assignedById);
            return computerAssignmentRepository.findByIdRangeAndAssignedBy(startId, endId, assignedBy);
        }
    }
}
