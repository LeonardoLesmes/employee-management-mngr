package com.employee_management_mngr.computer.infrastructure.adapters.output;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.employee_management_mngr.computer.application.ports.output.ComputerAssignmentRepository;
import com.employee_management_mngr.computer.domain.Computer;
import com.employee_management_mngr.computer.domain.ComputerAssignment;
import com.employee_management_mngr.computer.domain.ComputerAssignmentStatus;
import com.employee_management_mngr.employee.domain.employee.Employee;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PostgresComputerAssignmentRepository implements ComputerAssignmentRepository {
    private final EntityManager em;

    @Override
    public ComputerAssignment save(ComputerAssignment assignment) {
        if (assignment.getId() == null) {
            em.persist(assignment);
            return assignment;
        } else {
            return em.merge(assignment);
        }
    }

    @Override
    public Optional<ComputerAssignment> findById(Integer id) {
        return Optional.ofNullable(em.find(ComputerAssignment.class, id));
    }

    @Override
    public List<ComputerAssignment> findByEmployee(Employee employee) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ComputerAssignment> query = cb.createQuery(ComputerAssignment.class);
        Root<ComputerAssignment> root = query.from(ComputerAssignment.class);
        
        query.where(cb.equal(root.get("employee"), employee));
        
        return em.createQuery(query).getResultList();
    }

    @Override
    public List<ComputerAssignment> findByComputer(Computer computer) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ComputerAssignment> query = cb.createQuery(ComputerAssignment.class);
        Root<ComputerAssignment> root = query.from(ComputerAssignment.class);
        
        query.where(cb.equal(root.get("computer"), computer));
        
        return em.createQuery(query).getResultList();
    }

    @Override
    public Optional<ComputerAssignment> findActiveAssignmentByComputer(Computer computer) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ComputerAssignment> query = cb.createQuery(ComputerAssignment.class);
        Root<ComputerAssignment> root = query.from(ComputerAssignment.class);
        
        query.where(
            cb.and(
                cb.equal(root.get("computer"), computer),
                cb.equal(root.get("status"), ComputerAssignmentStatus.APPROVED),
                cb.isNull(root.get("returnDate"))
            )
        );
        
        try {
            return Optional.of(em.createQuery(query)
                .setMaxResults(1)
                .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
