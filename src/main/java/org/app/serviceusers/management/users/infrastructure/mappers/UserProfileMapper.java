package org.app.serviceusers.management.users.infrastructure.mappers;

import org.app.serviceusers.management.users.domain.models.UserProfile;
import org.app.serviceusers.management.users.domain.valueobjects.Date;
import org.app.serviceusers.management.users.infrastructure.adapters.ports.outputs.persistance.entities.DateEntity;
import org.app.serviceusers.management.users.infrastructure.adapters.ports.outputs.persistance.entities.UserProfileEntity;
import org.app.serviceusers.management.users.infrastructure.mappers.interfaces.IBaseMapper;
import org.springframework.stereotype.Component;

@Component("infrastructureUserProfileMapper")
public class UserProfileMapper implements IBaseMapper<UserProfile, UserProfileEntity> {

    private final DateMapper dateMapper;

    public UserProfileMapper(DateMapper dateMapper) {
        this.dateMapper = dateMapper;
    }

    @Override
    public UserProfileEntity toEntity(UserProfile domain) {
        UserProfileEntity entity = new UserProfileEntity();
        if (domain.getUuid() != null) {
            entity.setUuid(domain.getUuid());
        }
        entity.setUsername(domain.getUsername());
        entity.setBirthDate(domain.getBirthDate());
        entity.setCity(domain.getCity());
        entity.setProfilePicture(domain.getProfilePicture());

        DateEntity dateEntity = dateMapper.toEntity(domain.getDate());

        entity.setDate(dateEntity);
        dateEntity.setUserProfile(entity);
        return entity;
    }

    @Override
    public UserProfile toDomain(UserProfileEntity entity) {
        UserProfile domain = new UserProfile();
        if(entity.getUuid() != null) {
            domain.setUuid(entity.getUuid());
        }
        domain.setUsername(entity.getUsername());
        domain.setBirthDate(entity.getBirthDate());
        domain.setCity(entity.getCity());
        domain.setProfilePicture(entity.getProfilePicture());

        Date dateDomain = dateMapper.toDomain(entity.getDate());
        domain.setDate(dateDomain);
        dateDomain.setUserProfile(domain);
        return domain;
    }

}