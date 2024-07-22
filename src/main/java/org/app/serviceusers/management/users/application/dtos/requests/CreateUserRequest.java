package org.app.serviceusers.management.users.application.dtos.requests;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CreateUserRequest {

    @NotBlank(message = "Username cannot be blank")
    @NotNull(message = "Username cannot be null or empty")
    @Size(max = 100)
    private String username;

    @NotBlank(message = "Email cannot be blank")
    @NotNull(message = "Email cannot be null or empty")
    @Email(message = "Must be a correctly formatted e-mail address")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @NotNull(message = "Password cannot be null or empty")
    @Size(min = 8, message = "Password must contain 8 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "Password must only contain letters, numbers, and underscores")
    private String password;

    @NotBlank(message = "Password cannot be blank")
    @NotNull(message = "Password cannot be null or empty")
    @Size(min = 8, message = "Password must contain 8 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "Password must only contain letters, numbers, and underscores")
    private String confirmPassword;

    @NotNull(message = "City cannot be null")
    @NotBlank(message = "City cannot be blank")
    private String city;

}