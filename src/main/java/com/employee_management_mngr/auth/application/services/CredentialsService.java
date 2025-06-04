package com.employee_management_mngr.auth.application.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.employee_management_mngr.auth.application.dtos.AuthRequest;
import com.employee_management_mngr.auth.application.exceptions.AuthenticationException;
import com.employee_management_mngr.auth.application.ports.output.AuthRepository;
import com.employee_management_mngr.auth.domain.Credentials;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CredentialsService {
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;

    public CredentialsService(AuthRepository authRepository, PasswordEncoder passwordEncoder) {
        this.authRepository = authRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void isValidCredentials(String email, String password) {
        Credentials credentials = this.findByEmail(email);
        if (credentials.getPasswordHash() == null || credentials.getPasswordHash().isEmpty()) {
            throw new AuthenticationException("Password is not set for this employee");
        }
        if (!Boolean.TRUE.equals(credentials.getIsActive())) {
            throw new AuthenticationException("Account is inactive");
        }
        if (!passwordEncoder.matches(password, credentials.getPasswordHash())) {
            throw new AuthenticationException("Invalid credentials");
        }
    }

    public void createPassword(AuthRequest request) {
        Credentials credentials = this.findByEmail(request.getEmail());
        if (credentials.getPasswordHash() != null && !credentials.getPasswordHash().isEmpty()) {
            throw new AuthenticationException("Password already exists for this employee");
        }
        String passwordHash = passwordEncoder.encode(request.getPassword());
        credentials.setPasswordHash(passwordHash);
        authRepository.save(credentials);
    }

    public Credentials findByEmail(String email) {
        return authRepository.findByEmployeeEmail(email)
                .orElseThrow(() -> new AuthenticationException("User not found"));
    }
}
