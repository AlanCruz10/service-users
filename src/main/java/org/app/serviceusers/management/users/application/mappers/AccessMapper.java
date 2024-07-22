package org.app.serviceusers.management.users.application.mappers;

import org.app.serviceusers.management.users.application.dtos.requests.CreateAccessRequest;
import org.app.serviceusers.management.users.domain.models.Access;
import org.app.serviceusers.management.users.domain.models.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AccessMapper {

    public Access toDomain(CreateAccessRequest request) {
        Access access = new Access();
        access.setUser(request.getUser());
        access.setLastLogOut(request.getLastLogOut());
        access.setLastLogIn(request.getLastLogIn());
        return access;
    }

    public CreateAccessRequest toDto(LocalDateTime lastLogIn, LocalDateTime lastLogOut, User user) {
        CreateAccessRequest request = new CreateAccessRequest();
        request.setLastLogIn(lastLogIn);
        request.setLastLogOut(lastLogOut);
        request.setUser(user);
        return request;
    }

}