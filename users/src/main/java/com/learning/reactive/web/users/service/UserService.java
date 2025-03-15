package com.learning.reactive.web.users.service;

import com.learning.reactive.web.users.presentation.CreateUserRequest;
import com.learning.reactive.web.users.presentation.UserRest;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<UserRest> createUser(Mono<CreateUserRequest> createUserRequestMono);
}
