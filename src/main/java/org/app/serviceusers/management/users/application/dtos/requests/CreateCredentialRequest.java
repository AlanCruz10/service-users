package org.app.serviceusers.management.users.application.dtos.requests;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.app.serviceusers.management.users.domain.models.User;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CreateCredentialRequest {

    @NotNull(message = "Email is mandatory")
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @NotNull(message = "Password cannot be null or empty")
    @Size(min = 8, message = "Password must contain 8 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "Password must only contain letters, numbers, and underscores")
    private String password;

}