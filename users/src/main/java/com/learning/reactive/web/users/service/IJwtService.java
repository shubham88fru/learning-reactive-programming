package com.learning.reactive.web.users.service;

import reactor.core.publisher.Mono;

public interface IJwtService {
    String generateJwtToken(String subject);
    Mono<Boolean> validateJwt(String jwt);
    String extractJwtToken(String jwt);
}
