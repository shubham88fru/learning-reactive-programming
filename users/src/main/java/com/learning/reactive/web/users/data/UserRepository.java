package com.learning.reactive.web.users.data;

import com.learning.reactive.web.users.presentation.UserRest;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserRepository extends ReactiveCrudRepository<UserEntity, UUID> {
    Mono<UserRest> getUserEntityById(UUID id);
}
