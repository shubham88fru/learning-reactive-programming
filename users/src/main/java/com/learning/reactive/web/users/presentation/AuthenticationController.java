package com.learning.reactive.web.users.presentation;

import com.learning.reactive.web.users.presentation.model.AuthenticationRequest;
import com.learning.reactive.web.users.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public Mono<ResponseEntity<Object>> login(@RequestBody Mono<AuthenticationRequest> authenticationRequest) {
        return authenticationRequest
                .flatMap(request
                        -> authenticationService.authenticate(request.getEmail(), request.getPassword()))
                .map(mp -> ResponseEntity.ok()
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + mp.get("token"))
                                .header("UserId", mp.get("userId")).
                        build());
//                .onErrorReturn(BadCredentialsException.class, ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                        .body("Invalid Credentials"))
//                .onErrorReturn(Exception.class, ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }
}
