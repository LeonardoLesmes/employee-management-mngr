package com.employee_management_mngr.auth.infrastructure.ports.outputs;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.employee_management_mngr.auth.application.ports.output.AuthRepository;
import com.employee_management_mngr.auth.domain.Credentials;
import com.employee_management_mngr.employee.domain.employee.Employee;

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
    public Optional<Credentials> findByEmployeeEmail(String email) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Credentials> query = cb.createQuery(Credentials.class);
        
        Root<Credentials> credentials = query.from(Credentials.class);
        Join<Credentials, Employee> employee = credentials.join("employee");
        
        query.where(cb.equal(employee.get("email"), email));
        
        try {
            return Optional.of(em.createQuery(query).getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Credentials> save(Credentials credentials) {
        try {
            em.persist(credentials);
            return Optional.of(credentials);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
