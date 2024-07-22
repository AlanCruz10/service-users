package org.app.serviceusers.management.users.application.dtos.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {

    private String uuid;

    private String username;

    private String email;

    private String city;

    private String profilePicture;

}