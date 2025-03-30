package com.learning.reactive.web.users.presentation;

import com.learning.reactive.web.users.infrastructure.TestSecurityConfig;
import com.learning.reactive.web.users.presentation.model.CreateUserRequest;
import com.learning.reactive.web.users.presentation.model.UserRest;
import com.learning.reactive.web.users.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Import(TestSecurityConfig.class)
@WebFluxTest(UserController.class)
class UserControllerTest {

    @MockitoBean
    private UserService userService;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testCrateUser_withValidRequest_returnsCreatedStatusAndUserDetails() {
        // Arrange
        CreateUserRequest createUserRequest = new CreateUserRequest(
                "Shubham",
                "Singh",
                "@@@.com",
                "123456789"
        );

        UUID uuid = UUID.randomUUID();
        String expectedLocation = "/users/" + uuid;
        UserRest expectedUser = new UserRest(
                uuid,
                createUserRequest.getFirstName(),
                createUserRequest.getLastName(),
                createUserRequest.getEmail()
        );

        Mockito.when(userService.createUser(Mockito.<Mono<CreateUserRequest>>any()))
                .thenReturn(Mono.just(expectedUser));

        // Act
        webTestClient.post()
                .uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(createUserRequest)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().location(expectedLocation)
                .expectBody(UserRest.class)
                .value(response -> {
                    assertEquals(expectedUser.getFirstName(), response.getFirstName());
                    assertEquals(expectedUser.getLastName(), response.getLastName());
                    assertEquals(expectedUser.getEmail(), response.getEmail());
                    assertEquals(uuid, response.getId());
                });

        // Assert
        Mockito.verify(userService, Mockito.times(1))
                .createUser(Mockito.<Mono<CreateUserRequest>>any());
    }
}