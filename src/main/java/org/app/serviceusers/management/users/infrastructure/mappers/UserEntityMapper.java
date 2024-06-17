package org.app.serviceusers.management.users.infrastructure.mappers;

import org.app.serviceusers.management.users.domain.models.User;
import org.app.serviceusers.management.users.infrastructure.persistance.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserEntityMapper {

    public User toDomain(UserEntity entity) {
        User user = new User();
        user.setUuid(entity.getUuid());
        user.setUsername(entity.getUsername());
        user.setEmail(entity.getEmail());
        user.setPassword(entity.getPassword());
        return user;
    }

    public UserEntity toEntity(User user) {
        UserEntity entity = new UserEntity();
        entity.setUuid(user.getUuid());
        entity.setUsername(user.getUsername());
        entity.setEmail(user.getEmail());
        entity.setPassword(user.getPassword());
        return entity;
    }

}