package com.learning.reactive.web.users.presentation;

import com.learning.reactive.web.users.presentation.model.CreateUserRequest;
import com.learning.reactive.web.users.presentation.model.UserRest;
import com.learning.reactive.web.users.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

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
        return userService.createUser(createUserRequest)
                .map(userRest -> ResponseEntity
                        .status(HttpStatus.CREATED)
                        .location(URI.create("/users/" + userRest.getId()))
                        .body(userRest));



        /*
            return createUserRequest.map((request) -> {
                return new UserRest(UUID.randomUUID(), request.getFirstName(),
                    request.getLastName(), request.getEmail());
            }).map((userRest) -> ResponseEntity.status(HttpStatus.CREATED).body(userRest));
         */
    }

    @GetMapping("/{userId}")
    public Mono<ResponseEntity<UserRest>> getUser(@PathVariable UUID userId) {

        return userService.getUserById(userId)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build())); //return a different mono if og mono is null/empty.
        /*
            return Mono.just(new UserRest(userId, "Shubham", "Singh", "shubham@gmail.com"));
         */
    }

    @GetMapping
    public Flux<UserRest> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
                                   @RequestParam(value = "limit", defaultValue = "50") int limit) {

        return userService.findAll(page, limit);

        /*
            return Flux.just(
                    new UserRest(UUID.randomUUID(), "Shubham", "Singh", "test@test.com"),
                    new UserRest(UUID.randomUUID(), "Shubham2", "Singh2", "test2@test.com"),
                    new UserRest(UUID.randomUUID(), "Shubham3", "Singh3", "test3@test.com")
            );
         */
    }
}
