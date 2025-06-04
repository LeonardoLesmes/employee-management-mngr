package com.employee_management_mngr.auth.infrastructure.ports.inputs;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee_management_mngr.auth.application.ports.input.AuthUseCase;
import com.employee_management_mngr.auth.application.ports.input.dtos.AuthRequest;
import com.employee_management_mngr.auth.application.ports.input.dtos.AuthResponse;
import com.employee_management_mngr.auth.application.ports.input.dtos.CreateManagerPasswordRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthUseCase authService;

    public AuthController(AuthUseCase authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        AuthResponse response = authService.authenticateManager(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/set-password")
    public ResponseEntity<Void> createManagerPassword(@RequestBody CreateManagerPasswordRequest request) {
        authService.createManagerPassword(request);
        return ResponseEntity.ok().build();
    }
}