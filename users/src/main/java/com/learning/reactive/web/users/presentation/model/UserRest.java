package com.learning.reactive.web.users.presentation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRest {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;


}
