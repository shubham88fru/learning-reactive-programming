package com.learning.reactive.web.users.service;

import com.learning.reactive.web.users.data.UserEntity;
import com.learning.reactive.web.users.data.UserRepository;
import com.learning.reactive.web.users.presentation.model.CreateUserRequest;
import com.learning.reactive.web.users.presentation.model.UserRest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    /*
        @Mockito bean annotation is used in integration tests.
        It replaces the actual bean in spring application
        context with the annotated bean.

        @Mock bean annotation is used in unit tests.
        Since unit tests don't load application context,
        this bean doesn't replace any bean in the application
        context.
     */
    @Mock // vs @MockitoBean
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private Sinks.Many<UserRest> userSink;
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        userSink = Sinks.many().unicast().onBackpressureBuffer();
        userService = new UserServiceImpl(userRepository, passwordEncoder, userSink);
    }

    @Test //test<Name or System under test>_<Condition or state change>_<Expected result>
    void testCreateUser_withValidRequest_returnsCreatedUserDetails() {
        // Arrange
        CreateUserRequest createUserRequest = new CreateUserRequest(
                "Shubham",
                "Singh",
                "@@@.com",
                "123456789"
        );

        UserEntity savedEntity = new UserEntity();
        BeanUtils.copyProperties(createUserRequest, savedEntity);
        savedEntity.setId(UUID.randomUUID());

        Mockito.when(passwordEncoder.encode(any()))
                .thenReturn("encodedPassword");

        Mockito.when(userRepository.save(any()))
                .thenReturn(Mono.just(savedEntity));

        // Act
        Mono<UserRest> result = userService.createUser(Mono.just(createUserRequest));

        // Assert
        /*
            Non blocking.
         */
        StepVerifier.create(result)
                .expectNextMatches(userRest -> userRest.getId().equals(savedEntity.getId())
                && userRest.getEmail().equals(createUserRequest.getEmail())
                && userRest.getFirstName().equals(createUserRequest.getFirstName())
                && userRest.getLastName().equals(createUserRequest.getLastName()))
                .verifyComplete();

        Mockito.verify(userRepository, Mockito.times(1)).save(any());
        /*
            Blocking.
         */

        /*
            UserRest user = result.block();
            assertEquals(savedEntity.getId(), user.getId());
            assertEquals(savedEntity.getEmail(), user.getEmail());
            assertEquals(savedEntity.getFirstName(), user.getFirstName());
            assertEquals(savedEntity.getLastName(), user.getLastName());
        */
    }
}