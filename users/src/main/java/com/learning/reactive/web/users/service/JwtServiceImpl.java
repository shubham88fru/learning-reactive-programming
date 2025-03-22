package com.learning.reactive.web.users.service;

import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Service
@AllArgsConstructor
public class JwtServiceImpl implements IJwtService {
    private final Environment env;

    @Override
    public String generateJwtToken(String subject) {
        return Jwts.builder()
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(Date.from(Instant.now().plus(1, ChronoUnit.HOURS)))
                .signWith(getSigningKey())
                .compact();
    }

    private SecretKey getSigningKey() {
        return Optional.ofNullable(env.getProperty("jwt.token.secret"))
                .map((tokenSecret) -> tokenSecret.getBytes())
                .map(bytes -> Keys.hmacShaKeyFor(bytes))
                .orElseThrow(() -> new IllegalArgumentException("jwt.token.secret not found"));
    }
}
