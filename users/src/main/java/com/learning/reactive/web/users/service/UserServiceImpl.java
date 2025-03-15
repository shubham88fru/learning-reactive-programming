package com.learning.reactive.web.users.service;

import com.learning.reactive.web.users.data.UserEntity;
import com.learning.reactive.web.users.data.UserRepository;
import com.learning.reactive.web.users.presentation.CreateUserRequest;
import com.learning.reactive.web.users.presentation.UserRest;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

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
