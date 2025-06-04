package com.employee_management_mngr.computer.application.ports.output;

import java.util.List;
import java.util.Optional;
import com.employee_management_mngr.computer.domain.ComputerAssignment;
import com.employee_management_mngr.computer.domain.Computer;
import com.employee_management_mngr.employee.domain.employee.Employee;

public interface ComputerAssignmentRepository {
    ComputerAssignment save(ComputerAssignment assignment);
    Optional<ComputerAssignment> findById(Integer id);
    List<ComputerAssignment> findByEmployee(Employee employee);
    List<ComputerAssignment> findByComputer(Computer computer);
    List<ComputerAssignment> findByAssignedBy(Integer assignedBy);
}
