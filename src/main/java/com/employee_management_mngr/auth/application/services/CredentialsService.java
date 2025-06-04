package com.employee_management_mngr.auth.application.services;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.employee_management_mngr.auth.application.exceptions.AuthenticationException;
import com.employee_management_mngr.auth.application.ports.input.ManagerUseCase;
import com.employee_management_mngr.auth.application.ports.input.dtos.AuthRequest;
import com.employee_management_mngr.auth.application.ports.output.AuthRepository;
import com.employee_management_mngr.auth.domain.Manager;
import com.employee_management_mngr.auth.domain.ManagerCredentials;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class CredentialsService {
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final ManagerUseCase managerUseCase;

    public void isValidCredentials(String email, String password) {
        ManagerCredentials credentials = this.findByEmail(email);
        if (credentials.getPasswordHash() == null || credentials.getPasswordHash().isEmpty()) {
            throw new AuthenticationException("Password is not set for this manager");
        }
        if (!Boolean.TRUE.equals(credentials.getManager().getIsActive())) {
            throw new AuthenticationException("Account is inactive");
        }
        if (!passwordEncoder.matches(password, credentials.getPasswordHash())) {
            throw new AuthenticationException("Invalid credentials");
        }
    }

    public void createPassword(AuthRequest request) {
        Optional<ManagerCredentials> existingCredentials = authRepository.findByManagerEmail(request.getEmail());
        
        ManagerCredentials credentials;
        
        if (existingCredentials.isPresent()) {
            credentials = existingCredentials.get();
            if (credentials.getPasswordHash() != null && !credentials.getPasswordHash().isEmpty()) {
                throw new AuthenticationException("Password already exists for this manager");
            }
        } else {
            Manager manager = managerUseCase.findManagerByEmail(request.getEmail());
            
            credentials = new ManagerCredentials();
            credentials.setManager(manager);
        }
        String passwordHash = passwordEncoder.encode(request.getPassword());
        credentials.setPasswordHash(passwordHash);
        authRepository.save(credentials);
    }

    public ManagerCredentials findByEmail(String email) {
        return authRepository.findByManagerEmail(email)
                .orElseThrow(() -> new AuthenticationException("Manager not found"));
    }
}
