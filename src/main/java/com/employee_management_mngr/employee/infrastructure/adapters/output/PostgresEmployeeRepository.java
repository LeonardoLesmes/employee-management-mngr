package com.employee_management_mngr.employee.infrastructure.adapters.output;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.employee_management_mngr.employee.application.ports.output.EmployeeRepository;
import com.employee_management_mngr.employee.domain.Employee;

@Repository
public interface PostgresEmployeeRepository extends JpaRepository<Employee, Long>, EmployeeRepository {
    Optional<Employee> findByEmail(String email);
}
