package com.employee_management_mngr.auth.infrastructure.inputs;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee_management_mngr.auth.application.dtos.AuthRequest;
import com.employee_management_mngr.auth.application.dtos.AuthResponse;
import com.employee_management_mngr.auth.application.ports.input.AuthUseCase;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthUseCase authService;

    public AuthController(AuthUseCase authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        AuthResponse response = authService.authenticate(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create-password")
    public ResponseEntity<Void> createPassword(@RequestBody AuthRequest request) {
        authService.createPassword(request);
        return ResponseEntity.ok().build();
    }
}