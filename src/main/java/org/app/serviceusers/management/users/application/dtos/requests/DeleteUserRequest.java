package org.app.serviceusers.management.users.application.dtos.requests;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DeleteUserRequest {

    @NotBlank(message = "User UUID is required")
    @NotNull(message = "User UUID is required")
    private String uuid;

}