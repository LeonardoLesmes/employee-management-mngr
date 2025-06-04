package com.employee_management_mngr.auth.application.orchestrators;

import org.springframework.stereotype.Component;

import com.employee_management_mngr.auth.application.exceptions.AuthenticationException;
import com.employee_management_mngr.auth.application.ports.input.ManagerUseCase;
import com.employee_management_mngr.auth.domain.Manager;
import jakarta.transaction.Transactional;

@Component
@Transactional
public class ManagerOrchestrator implements ManagerUseCase {

    private final com.employee_management_mngr.auth.application.ports.output.ManagerRepository managerRepository;

    public ManagerOrchestrator(
            com.employee_management_mngr.auth.application.ports.output.ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    @Override
    public Manager findManagerByEmail(String email) {
        return managerRepository.findByEmail(email).orElseThrow(() -> new AuthenticationException("Manager not found"));
    }
}
