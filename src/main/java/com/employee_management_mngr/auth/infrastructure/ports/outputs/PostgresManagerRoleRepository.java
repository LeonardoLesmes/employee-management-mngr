package com.employee_management_mngr.auth.infrastructure.ports.outputs;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.employee_management_mngr.auth.application.ports.output.ManagerRoleRepository;
import com.employee_management_mngr.auth.domain.ManagerRole;

import jakarta.persistence.EntityManager;

@Repository
public class PostgresManagerRoleRepository implements ManagerRoleRepository {
    
    private final EntityManager em;
    
    public PostgresManagerRoleRepository(EntityManager em) {
        this.em = em;
    }
    
    @Override
    public Optional<ManagerRole> findById(Integer id) {
        try {
            return Optional.of(em.find(ManagerRole.class, id));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
