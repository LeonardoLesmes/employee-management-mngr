package com.employee_management_mngr.computer.infrastructure.adapters.output;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.employee_management_mngr.computer.application.ports.output.ComputerRepository;
import com.employee_management_mngr.computer.domain.Computer;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PostgresComputerRepository implements ComputerRepository {
    private final EntityManager em;

    @Override
    public Computer save(Computer computer) {
        if (computer.getId() == null) {
            em.persist(computer);
            return computer;
        } else {
            return em.merge(computer);
        }
    }

    @Override
    public Optional<Computer> findById(Integer id) {
        return Optional.ofNullable(em.find(Computer.class, id));
    }

    @Override
    public List<Computer> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Computer> query = cb.createQuery(Computer.class);
        Root<Computer> root = query.from(Computer.class);
        return em.createQuery(query).getResultList();
    }

    @Override
    public List<Computer> findAvailableComputers() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Computer> query = cb.createQuery(Computer.class);
        Root<Computer> root = query.from(Computer.class);

        query.where(cb.equal(root.get("status"), "AVAILABLE"));

        return em.createQuery(query).getResultList();
    }
}
