package com.learning.reactive.web.users.presentation;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @PostMapping
    public Mono<ResponseEntity<UserRest>> createUser(@RequestBody @Valid Mono<CreateUserRequest> createUserRequest) {

        /*
            -------------------
            IMPERATIVE/BLOCKING
            -------------------
            UserRest userRest = new UserRest();
            return Mono.just(userRest);
         */

        /*
            -----------------------------------
            DECLARATIVE/FUNCTIONAL/NON-BLOCKING
            -----------------------------------
         */
        return createUserRequest.map((request) -> {
            return new UserRest(UUID.randomUUID(), request.getFirstName(),
                    request.getLastName(), request.getEmail());
        }).map((userRest) -> ResponseEntity.status(HttpStatus.CREATED).body(userRest));
    }

    @GetMapping("/{userId}")
    public Mono<UserRest> getUser(@PathVariable UUID userId) {
        return Mono.just(new UserRest(userId, "Shubham", "Singh", "shubham@gmail.com"));
    }

    @GetMapping
    public Flux<UserRest> getUsers(@RequestParam(value="offset", defaultValue = "0") int offset,
                                   @RequestParam(value="limit", defaultValue = "50") int limit) {
        return Flux.just(
                new UserRest(UUID.randomUUID(), "Shubham", "Singh", "test@test.com"),
                new UserRest(UUID.randomUUID(), "Shubham2", "Singh2", "test2@test.com"),
                new UserRest(UUID.randomUUID(), "Shubham3", "Singh3", "test3@test.com")
        );
    }
}
