package com.employee_management_mngr.auth.infrastructure.ports.outputs;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.employee_management_mngr.auth.application.ports.output.AuthRepository;
import com.employee_management_mngr.auth.domain.Manager;
import com.employee_management_mngr.auth.domain.ManagerCredentials;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;

@Repository
public class PostgresAuthRepository implements AuthRepository {
    private final EntityManager em;

    public PostgresAuthRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<ManagerCredentials> findByManagerEmail(String email) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ManagerCredentials> query = cb.createQuery(ManagerCredentials.class);

        Root<ManagerCredentials> credentials = query.from(ManagerCredentials.class);
        Join<ManagerCredentials, Manager> manager = credentials.join("manager");

        query.where(cb.equal(manager.get("email"), email));

        try {
            return Optional.of(em.createQuery(query).getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<ManagerCredentials> save(ManagerCredentials credentials) {
        try {
            em.persist(credentials);
            return Optional.of(credentials);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
