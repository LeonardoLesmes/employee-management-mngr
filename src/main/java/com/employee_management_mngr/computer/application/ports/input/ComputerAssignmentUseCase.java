package com.employee_management_mngr.computer.application.ports.input;

import java.util.List;
import com.employee_management_mngr.computer.domain.Computer;
import com.employee_management_mngr.computer.domain.ComputerAssignment;
import com.employee_management_mngr.computer.domain.ComputerAssignmentStatus;

public interface ComputerAssignmentUseCase {
    ComputerAssignment createAssignment(Integer employeeId, Integer computerId, Integer assignedById);

    ComputerAssignment updateAssignmentStatus(Integer assignmentId, ComputerAssignmentStatus status);

    List<ComputerAssignment> findByEmployeeId(Integer employeeId);

    List<ComputerAssignment> findByAssignedById(Integer assignedById);

    List<ComputerAssignment> findActiveAssignments();

    List<Computer> findAvailableComputers();

    List<ComputerAssignment> findByIdRange(Integer startId, Integer endId);

    List<ComputerAssignment> findByIdRangeAndAssignedBy(Integer startId, Integer endId, Integer assignedById);
}
