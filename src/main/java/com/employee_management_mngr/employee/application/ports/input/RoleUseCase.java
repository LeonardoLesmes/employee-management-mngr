package com.employee_management_mngr.employee.application.ports.input;

import com.employee_management_mngr.employee.domain.role.Role;
import java.util.List;

public interface RoleUseCase {
    Role findById(Integer id);
    List<Role> findAll();
    Role save(Role role);
    void deleteById(Integer id);
}
