package com.learning.reactive.web.users.service;

import com.learning.reactive.web.users.data.UserEntity;
import com.learning.reactive.web.users.data.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final ReactiveAuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtServiceImpl jwtService;

    @Override
    public Mono<Map<String, String>> authenticate(String username, String password) {
        return authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password))
                .then(getUserDetails(username))
                .map(this::createAuthResponse);
    }

    private Mono<UserEntity> getUserDetails(String username) {
        return userRepository.findByEmail(username);
    }

    private Map<String, String> createAuthResponse(UserEntity user) {
        Map<String, String> result = new HashMap<>();

        result.put("userId", user.getId().toString());
        result.put("token", jwtService.generateJwtToken(user.getId().toString()));

        return result;
    }
}
