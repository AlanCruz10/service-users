package org.app.serviceusers.management.users.infrastructure.mappers;

import org.app.serviceusers.management.users.domain.models.Credential;
import org.app.serviceusers.management.users.domain.models.User;
import org.app.serviceusers.management.users.domain.models.UserProfile;
import org.app.serviceusers.management.users.infrastructure.adapters.ports.outputs.persistance.entities.CredentialEntity;
import org.app.serviceusers.management.users.infrastructure.adapters.ports.outputs.persistance.entities.UserEntity;
import org.app.serviceusers.management.users.infrastructure.adapters.ports.outputs.persistance.entities.UserProfileEntity;
import org.app.serviceusers.management.users.infrastructure.mappers.interfaces.IBaseMapper;
import org.springframework.stereotype.Component;

@Component("infrastructureUserMapper")
public class UserMapper implements IBaseMapper<User, UserEntity> {

    private final CredentialMapper credentialMapper;
    private final UserProfileMapper userProfileMapper;
    private final AccessMapper accessMapper;

    public UserMapper(CredentialMapper credentialMapper, UserProfileMapper userProfileMapper, AccessMapper accessMapper) {
        this.credentialMapper = credentialMapper;
        this.userProfileMapper = userProfileMapper;
        this.accessMapper = accessMapper;
    }

    @Override
    public UserEntity toEntity(User domain) {
        UserEntity entity = new UserEntity();
        if (domain.getUuid() != null) {
            entity.setUuid(domain.getUuid());
        }
        entity.setFirstSignIn(domain.getFirstSignIn());

        CredentialEntity credentialEntity = credentialMapper.toEntity(domain.getCredential());

        UserProfileEntity userProfileEntity = userProfileMapper.toEntity(domain.getUserProfile());

        credentialEntity.setUser(entity);
        userProfileEntity.setUser(entity);

        entity.setAccesses(domain.getAccesses().stream()
                .map(accessMapper::toEntity).peek(accessEntity -> {
                    if (accessEntity != null) accessEntity.setUser(entity);
                }).toList());

        entity.setCredential(credentialEntity);
        entity.setUserProfile(userProfileEntity);
        return entity;
    }

    @Override
    public User toDomain(UserEntity entity) {
        User domain = new User();
        if (entity.getUuid() != null) {
            domain.setUuid(entity.getUuid());
        }
        domain.setFirstSignIn(entity.getFirstSignIn());

        Credential credential = credentialMapper.toDomain(entity.getCredential());

        UserProfile userProfile = userProfileMapper.toDomain(entity.getUserProfile());

        credential.setUser(domain);
        userProfile.setUser(domain);

        domain.setAccesses(entity.getAccesses().stream()
                .map(accessMapper::toDomain).peek(access -> {
                    if (access != null) access.setUser(domain);
                }).toList());

        domain.setCredential(credential);
        domain.setUserProfile(userProfile);

        return domain;
    }

}