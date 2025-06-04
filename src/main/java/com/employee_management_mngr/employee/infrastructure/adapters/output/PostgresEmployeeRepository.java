package com.employee_management_mngr.employee.infrastructure.adapters.output;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.employee_management_mngr.employee.application.ports.output.EmployeeRepository;
import com.employee_management_mngr.employee.domain.employee.Employee;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;

@Repository
public class PostgresEmployeeRepository implements EmployeeRepository {
    private final EntityManager em;

    public PostgresEmployeeRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Employee> findByEmail(String email) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Employee> query = cb.createQuery(Employee.class);

        Root<Employee> employee = query.from(Employee.class);
        employee.fetch("role", JoinType.LEFT);

        query.where(cb.equal(cb.lower(employee.get("email")), email.toLowerCase()));

        try {
            return Optional.of(em.createQuery(query).getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Employee> findById(Integer id) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Employee> query = cb.createQuery(Employee.class);

        Root<Employee> employee = query.from(Employee.class);
        employee.fetch("role", JoinType.LEFT);

        query.where(cb.equal(employee.get("id"), id));

        try {
            return Optional.of(em.createQuery(query).getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Employee save(Employee employee) {
        if (employee.getId() == null) {
            em.persist(employee);
            return employee;
        } else {
            return em.merge(employee);
        }
    }

    @Override
    public List<Employee> findByAssignedBy(Integer assignedBy) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Employee> query = cb.createQuery(Employee.class);

        Root<Employee> employee = query.from(Employee.class);
        employee.fetch("role", JoinType.LEFT);

        query.where(cb.equal(employee.get("assignedBy"), assignedBy));

        return em.createQuery(query).getResultList();
    }

    @Override
    public List<Employee> findByIdRange(Integer startId, Integer endId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Employee> query = cb.createQuery(Employee.class);

        Root<Employee> employee = query.from(Employee.class);
        employee.fetch("role", JoinType.LEFT);

        query.where(cb.and(cb.greaterThanOrEqualTo(employee.get("id"), startId),
                cb.lessThanOrEqualTo(employee.get("id"), endId)));

        return em.createQuery(query).getResultList();
    }

    @Override
    public List<Employee> findByIdRangeAndAssignedBy(Integer startId, Integer endId, Integer assignedBy) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Employee> query = cb.createQuery(Employee.class);

        Root<Employee> employee = query.from(Employee.class);
        employee.fetch("role", JoinType.LEFT);

        query.where(cb.and(cb.greaterThanOrEqualTo(employee.get("id"), startId),
                cb.lessThanOrEqualTo(employee.get("id"), endId), cb.equal(employee.get("assignedBy"), assignedBy)));

        return em.createQuery(query).getResultList();
    }
}
