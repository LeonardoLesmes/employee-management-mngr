package com.employee_management_mngr.access.application.ports.output;

import java.util.Optional;
import com.employee_management_mngr.access.domain.AccessRequest;

public interface AccessRequestRepository {
    AccessRequest save(AccessRequest accessRequest);
    Optional<AccessRequest> findById(Integer id);
}
