package org.app.serviceusers.management.users.application.dtos.requests;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CreateUserProfileRequest {

    @NotNull(message = "Username cannot be null or empty")
    @NotBlank(message = "Username cannot be blank")
    @Size(max = 100)
    private String username;

    private LocalDate birthDate;

    @NotBlank(message = "City cannot be blank")
    @NotNull(message = "City cannot be null")
    private String city;

    private String profilePicture;

}