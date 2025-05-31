package com.employee_management_mngr.system.infrastructure.adapters.output;

import java.util.Optional;
import org.springframework.stereotype.Repository;
import com.employee_management_mngr.system.application.ports.output.SystemRepository;
import com.employee_management_mngr.system.domain.System;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PostgresSystemRepository implements SystemRepository {
    private final EntityManager em;

    @Override
    public Optional<System> findById(Integer id) {
        return Optional.ofNullable(em.find(System.class, id));
    }
}
