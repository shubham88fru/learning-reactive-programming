package com.learning.reactive.web.users.presentation;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @PostMapping
    public Mono<UserRest> createUser(@RequestBody @Valid Mono<CreateUserRequest> createUserRequest) {

        /*
            ----------
            IMPERATIVE
            ----------
            UserRest userRest = new UserRest();
            return Mono.just(userRest);
         */

        return createUserRequest.map((request) -> {
            return new UserRest(UUID.randomUUID(), request.getFirstName(),
                    request.getLastName(), request.getEmail());
        });
    }
}
