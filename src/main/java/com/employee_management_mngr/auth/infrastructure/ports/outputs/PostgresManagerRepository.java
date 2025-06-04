package com.employee_management_mngr.auth.infrastructure.ports.outputs;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.employee_management_mngr.auth.application.ports.output.ManagerRepository;
import com.employee_management_mngr.auth.domain.Manager;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

@Repository
public class PostgresManagerRepository implements ManagerRepository {
    private final EntityManager em;

    public PostgresManagerRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Manager> findByEmail(String email) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Manager> query = cb.createQuery(Manager.class);

        Root<Manager> manager = query.from(Manager.class);
        query.where(cb.equal(manager.get("email"), email));

        try {
            return Optional.of(em.createQuery(query).getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
    
    @Override
    @Transactional
    public Manager save(Manager manager) {
        if (manager.getId() == null) {
            em.persist(manager);
            return manager;
        } else {
            return em.merge(manager);
        }
    }
}
