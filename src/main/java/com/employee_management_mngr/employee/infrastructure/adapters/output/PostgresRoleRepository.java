package com.employee_management_mngr.employee.infrastructure.adapters.output;

import com.employee_management_mngr.employee.application.ports.output.RoleRepository;
import com.employee_management_mngr.employee.domain.role.Role;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;

interface RoleJpaRepository extends JpaRepository<Role, Integer> {
}

@Repository
@RequiredArgsConstructor
public class PostgresRoleRepository implements RoleRepository {
    private final RoleJpaRepository repository;

    @Override
    public Optional<Role> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<Role> findAll() {
        return repository.findAll();
    }

    @Override
    public Role save(Role role) {
        return repository.save(role);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
