package org.app.serviceusers.management.users.application.dtos.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UpdateUserRequest {

    @NotBlank(message = "UUID cannot be blank")
    @NotNull(message = "UUID cannot be null or empty")
    private String uuid;

    @Size(max = 100, message = "Username must be less than 100 characters")
    private String username;

    @Email(message = "Must be a correctly formatted e-mail address")
    private String email;

//    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "Password must only contain letters, numbers, and underscores")
    @Pattern(regexp = "^(?=.*[^_])[_a-zA-Z\\d]{8,}$|^$", message = "Password must be at least 8 characters long and contain at least one letter or number, and may include underscores, but cannot consist solely of underscores or be empty")
    private String password;

    @Pattern(regexp = "^(?=.*[^_])[_a-zA-Z\\d]{8,}$|^$", message = "Password must be at least 8 characters long and contain at least one letter or number, and may include underscores, but cannot consist solely of underscores or be empty")
    private String passwordConfirmation;

    private String city;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

}