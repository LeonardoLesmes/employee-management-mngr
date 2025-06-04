package com.employee_management_mngr.auth.infrastructure.inputs;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee_management_mngr.auth.application.dtos.TokenValidationRequest;
import com.employee_management_mngr.auth.application.ports.input.TokenValidationUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class TokenValidationController {

    private final TokenValidationUseCase tokenValidationUseCase;

    @PostMapping("/validate-token")
    public ResponseEntity<Boolean> validateToken(@RequestBody TokenValidationRequest request) {
        boolean isValid = tokenValidationUseCase.validateToken(request.getToken());
        return ResponseEntity.ok(isValid);
    }
}
