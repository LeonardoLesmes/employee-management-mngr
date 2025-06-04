package com.employee_management_mngr.system.infrastructure.adapters.output;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import com.employee_management_mngr.system.application.ports.output.SystemRepository;
import com.employee_management_mngr.system.domain.System;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PostgresSystemRepository implements SystemRepository {
    private final EntityManager em;

    @Override
    public Optional<System> findById(Integer id) {
        return Optional.ofNullable(em.find(System.class, id));
    }

    @Override
    public List<System> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<System> query = cb.createQuery(System.class);
        Root<System> root = query.from(System.class);
        query.select(root);
        return em.createQuery(query).getResultList();
    }
}
