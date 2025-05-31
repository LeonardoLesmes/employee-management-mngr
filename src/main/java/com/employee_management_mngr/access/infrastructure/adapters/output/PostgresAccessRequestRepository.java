package com.employee_management_mngr.access.infrastructure.adapters.output;

import java.util.Optional;
import org.springframework.stereotype.Repository;
import com.employee_management_mngr.access.application.ports.output.AccessRequestRepository;
import com.employee_management_mngr.access.domain.AccessRequest;
import jakarta.persistence.EntityManager;
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
}
