package org.app.serviceusers.management.users.application.mappers;

import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class MapperApplicationFactory {

    private static MapperApplicationFactory instance;
    private final UserMapper userMapper;
    private final UserProfileMapper userProfileMapper;
    private final CredentialMapper credentialMapper;
    private final AccessMapper accessMapper;
    private final DateMapper dateMapper;

    public MapperApplicationFactory(UserMapper userMapper, UserProfileMapper userProfileMapper, CredentialMapper credentialMapper, AccessMapper accessMapper, DateMapper dateMapper) {
        this.userMapper = userMapper;
        this.userProfileMapper = userProfileMapper;
        this.credentialMapper = credentialMapper;
        this.accessMapper = accessMapper;
        this.dateMapper = dateMapper;
    }

}