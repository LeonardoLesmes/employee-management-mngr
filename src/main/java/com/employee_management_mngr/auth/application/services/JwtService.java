package com.employee_management_mngr.auth.application.services;

import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expirationTimeMinutes;

    private SecretKey signedKey;

    @PostConstruct
    public void init() {
        this.signedKey = getSigningKey();
    }

    public String generateToken(Integer id, String role) {
        final long EXPIRATION_TIME = expirationTimeMinutes * 60 * 1000;
        return Jwts.builder().setSubject(id.toString()).setIssuedAt(new Date()).setId(UUID.randomUUID().toString())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)).signWith(signedKey).compact();
    }

    public boolean isValidToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(signedKey).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
