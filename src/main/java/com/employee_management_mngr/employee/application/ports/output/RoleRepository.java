package com.employee_management_mngr.employee.application.ports.output;

import com.employee_management_mngr.employee.domain.role.Role;
import java.util.List;
import java.util.Optional;

public interface RoleRepository {
    Optional<Role> findById(Integer id);

    List<Role> findAll();

    Role save(Role role);

    void deleteById(Integer id);
}
