package org.app.serviceusers.management.users.application.dtos.requests;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.app.serviceusers.management.users.domain.models.User;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CreateAccessRequest {

    private LocalDateTime lastLogIn;

    private LocalDateTime lastLogOut;

    @NotNull(message = "User is required")
    @NotBlank(message = "User is required")
    private User user;

}