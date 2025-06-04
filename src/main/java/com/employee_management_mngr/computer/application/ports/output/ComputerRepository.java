package com.employee_management_mngr.computer.application.ports.output;

import java.util.List;
import java.util.Optional;
import com.employee_management_mngr.computer.domain.Computer;

public interface ComputerRepository {
    Computer save(Computer computer);

    Optional<Computer> findById(Integer id);

    List<Computer> findAll();

    List<Computer> findAvailableComputers();
}
