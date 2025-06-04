package com.employee_management_mngr.access.infrastructure.adapters.output;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import com.employee_management_mngr.access.application.ports.output.AccessRequestRepository;
import com.employee_management_mngr.access.domain.AccessRequest;
import com.employee_management_mngr.access.domain.System;
import com.employee_management_mngr.employee.domain.employee.Employee;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PostgresAccessRequestRepository implements AccessRequestRepository {
    private final EntityManager em;

    @Override
    public AccessRequest save(AccessRequest accessRequest) {
        if (accessRequest.getId() == null) {
            em.persist(accessRequest);
            return accessRequest;
        } else {
            return em.merge(accessRequest);
        }
    }

    @Override
    public Optional<AccessRequest> findById(Integer id) {
        return Optional.ofNullable(em.find(AccessRequest.class, id));
    }

    @Override
    public Optional<AccessRequest> findByEmployeeAndSystem(Employee employee, System system) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<AccessRequest> query = cb.createQuery(AccessRequest.class);
        Root<AccessRequest> root = query.from(AccessRequest.class);

        query.where(cb.and(cb.equal(root.get("employee"), employee), cb.equal(root.get("system"), system)));

        try {
            return Optional.of(em.createQuery(query).setMaxResults(1).getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<AccessRequest> findByEmployeeId(Integer employeeId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<AccessRequest> query = cb.createQuery(AccessRequest.class);
        Root<AccessRequest> root = query.from(AccessRequest.class);

        query.where(cb.equal(root.get("employee").get("id"), employeeId));

        return em.createQuery(query).getResultList();
    }

    @Override
    public List<AccessRequest> findByAssignedById(Integer assignedById) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<AccessRequest> query = cb.createQuery(AccessRequest.class);
        Root<AccessRequest> root = query.from(AccessRequest.class);

        query.where(cb.equal(root.get("assignedBy").get("id"), assignedById));

        return em.createQuery(query).getResultList();
    }

    @Override
    public List<AccessRequest> findByIdRange(Integer startId, Integer endId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<AccessRequest> query = cb.createQuery(AccessRequest.class);
        Root<AccessRequest> root = query.from(AccessRequest.class);

        query.where(
                cb.and(cb.greaterThanOrEqualTo(root.get("id"), startId), cb.lessThanOrEqualTo(root.get("id"), endId)));

        return em.createQuery(query).getResultList();
    }

    @Override
    public List<AccessRequest> findByIdRangeAndAssignedBy(Integer startId, Integer endId, Employee assignedBy) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<AccessRequest> query = cb.createQuery(AccessRequest.class);
        Root<AccessRequest> root = query.from(AccessRequest.class);

        query.where(cb.and(cb.greaterThanOrEqualTo(root.get("id"), startId),
                cb.lessThanOrEqualTo(root.get("id"), endId), cb.equal(root.get("assignedBy"), assignedBy)));

        return em.createQuery(query).getResultList();
    }
}
