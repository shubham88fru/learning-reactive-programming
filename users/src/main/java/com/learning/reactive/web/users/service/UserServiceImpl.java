package com.learning.reactive.web.users.service;

import com.learning.reactive.web.users.data.UserEntity;
import com.learning.reactive.web.users.data.UserRepository;
import com.learning.reactive.web.users.presentation.CreateUserRequest;
import com.learning.reactive.web.users.presentation.UserRest;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Mono<UserRest> createUser(Mono<CreateUserRequest> createUserRequestMono) {
        //create user entity object


        return createUserRequestMono
                .map(this::convertToEntity)
                .flatMap(userRepository::save) //flatmap flattens nested monos into a single Mono.
                .map(this::convertToRest);
                //.onErrorMap(DuplicateKeyException.class,
                //ex -> new ResponseStatusException(HttpStatus.CONFLICT, "User already exists"));
//                .onErrorMap(throwable -> {//executes when error is thrown at a step above.
//                    if (throwable instanceof DuplicateKeyException) {
//                        return new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
//                    } else if (throwable instanceof DataIntegrityViolationException) {
//                        return new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request");
//                    }
//                    return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
//                });
    }

    @Override
    public Mono<UserRest> getUserById(UUID id) {
        return userRepository.findById(id).map(this::convertToRest);
    }

    @Override
    public Flux<UserRest> findAll(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        return userRepository.findAllBy(pageable).map(this::convertToRest);
    }

    private UserEntity convertToEntity(CreateUserRequest createUserRequest) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(createUserRequest, userEntity);
        return userEntity;
    }

    private UserRest convertToRest(UserEntity userEntity) {
        UserRest userRest = new UserRest();
        BeanUtils.copyProperties(userEntity, userRest);
        return userRest;
    }
}
