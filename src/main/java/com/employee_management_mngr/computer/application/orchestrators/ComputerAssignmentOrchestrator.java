package com.employee_management_mngr.computer.application.orchestrators;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.employee_management_mngr.computer.application.ports.input.ComputerAssignmentUseCase;
import com.employee_management_mngr.computer.application.services.ComputerAssignmentService;
import com.employee_management_mngr.computer.domain.Computer;
import com.employee_management_mngr.computer.domain.ComputerAssignment;
import com.employee_management_mngr.computer.domain.ComputerAssignmentStatus;

import lombok.RequiredArgsConstructor;

@Component
@Transactional
@RequiredArgsConstructor
public class ComputerAssignmentOrchestrator implements ComputerAssignmentUseCase {
    private final ComputerAssignmentService computerAssignmentService;

    @Override
    public ComputerAssignment createAssignment(Integer employeeId, Integer computerId, Integer assignedById) {
        return computerAssignmentService.createAssignment(employeeId, computerId, assignedById);
    }

    @Override
    public ComputerAssignment updateAssignmentStatus(Integer assignmentId, ComputerAssignmentStatus status) {
        return computerAssignmentService.updateAssignmentStatus(assignmentId, status);
    }

    @Override
    public List<ComputerAssignment> findByEmployeeId(Integer employeeId) {
        return computerAssignmentService.findByEmployeeId(employeeId);
    }

    @Override
    public List<ComputerAssignment> findByAssignedById(Integer assignedById) {
        return computerAssignmentService.findByAssignedById(assignedById);
    }

    @Override
    public List<Computer> findAvailableComputers() {
        return computerAssignmentService.findAvailableComputers();
    }
}
