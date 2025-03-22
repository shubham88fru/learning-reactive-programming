package com.learning.reactive.web.users.service;

public interface IJwtService {
    String generateJwtToken(String subject);
}
