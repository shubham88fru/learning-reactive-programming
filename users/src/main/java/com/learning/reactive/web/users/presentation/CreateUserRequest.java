package com.learning.reactive.web.users.presentation;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateUserRequest {
    @NotBlank(message = "First name cannot be empty")
    @Size(min = 2, max = 50, message = "First name cannot be shorter than 2 chars and longer than 50 chars.")
    private String firstName;

    @NotBlank(message = "Last name cannot be empty")
    @Size(min = 2, max = 50, message = "Last name cannot be shorter than 2 chars and longer than 50 chars.")
    private String lastName;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Please enter a valid email")
    private String email;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 8, max = 16, message = "Password must be atleast 8 and smaller than 16 chars.")
    private String password;
}
