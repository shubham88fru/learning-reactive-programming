package com.learning.reactive.web.users.presentation;

import com.learning.reactive.web.users.presentation.model.AuthenticationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class AuthenticationController {

    @PostMapping("/login")
    public Mono<ResponseEntity<Void>> login(@RequestBody Mono<AuthenticationRequest> authenticationRequest) {
        return Mono.just(ResponseEntity.ok().build());
    }
}
