package org.app.serviceusers.management.users.infrastructure.mappers;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class MapperInfrastructureFactory {

    private static MapperInfrastructureFactory instance;
    private UserMapper userMapper;
    private UserProfileMapper userProfileMapper;
    private CredentialMapper credentialMapper;
    private AccessMapper accessMapper;
    private DateMapper dateMapper;

    public MapperInfrastructureFactory(@Qualifier("infrastructureUserMapper") UserMapper userMapper,
                                       @Qualifier("infrastructureUserProfileMapper") UserProfileMapper userProfileMapper,
                                       @Qualifier("infrastructureCredentialMapper") CredentialMapper credentialMapper,
                                       @Qualifier("infrastructureAccessMapper") AccessMapper accessMapper,
                                       @Qualifier("infrastructureDateMapper") DateMapper dateMapper) {
        this.userMapper = userMapper;
        this.userProfileMapper = userProfileMapper;
        this.credentialMapper = credentialMapper;
        this.accessMapper = accessMapper;
        this.dateMapper = dateMapper;
    }

}