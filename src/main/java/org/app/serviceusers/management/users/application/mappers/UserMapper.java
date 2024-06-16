package org.app.serviceusers.management.users.application.mappers;

import org.app.serviceusers.management.users.application.dtos.requests.CreateUserRequest;
import org.app.serviceusers.management.users.application.dtos.responses.UserResponse;
import org.app.serviceusers.management.users.domain.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toDomain(CreateUserRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        return user;
    }

    public UserResponse toDto(User user) {
        return UserResponse.builder()
                .uuid(user.getUuid())
                .username(user.getUsername()).build();
    }

}