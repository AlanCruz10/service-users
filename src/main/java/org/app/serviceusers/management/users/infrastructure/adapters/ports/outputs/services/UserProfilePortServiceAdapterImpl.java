package org.app.serviceusers.management.users.infrastructure.adapters.ports.outputs.services;

import org.app.serviceusers.management.users.application.ports.outputs.IUserProfilePortOutputService;
import org.app.serviceusers.management.users.domain.models.UserProfile;
import org.app.serviceusers.management.users.infrastructure.adapters.ports.outputs.persistance.entities.UserProfileEntity;
import org.app.serviceusers.management.users.infrastructure.adapters.ports.outputs.persistance.repositories.jpa.IUserProfileJpaRepository;
import org.app.serviceusers.management.users.infrastructure.mappers.MapperInfrastructureFactory;
import org.app.serviceusers.management.users.infrastructure.mappers.UserProfileMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserProfilePortServiceAdapterImpl implements IUserProfilePortOutputService {

    private final IUserProfileJpaRepository repository;
    private final MapperInfrastructureFactory mapper;

    public UserProfilePortServiceAdapterImpl(IUserProfileJpaRepository repository, MapperInfrastructureFactory mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public UserProfile save(UserProfile entity) {
        UserProfileEntity save = repository.save(mapper.getUserProfileMapper().toEntity(entity));
        UserProfile userProfile = mapper.getUserProfileMapper().toDomain(save);
        userProfile.setUser(mapper.getUserMapper().toDomain(save.getUser()));
        return userProfile;
    }

    @Override
    public Optional<UserProfile> findById(String id) {
        UserProfileMapper userProfileMapper = mapper.getUserProfileMapper();
        return repository.findByUuid(id).map(userProfileEntity -> {
            UserProfile userProfile = userProfileMapper.toDomain(userProfileEntity);
            userProfile.setUser(mapper.getUserMapper().toDomain(userProfileEntity.getUser()));
            return userProfile;
        });
    }

    @Override
    public List<UserProfile> findAll() {
        return repository.findAll().stream().map(userProfileEntity -> {
            UserProfile userProfile = mapper.getUserProfileMapper().toDomain(userProfileEntity);
            userProfile.setUser(mapper.getUserMapper().toDomain(userProfileEntity.getUser()));
            return userProfile;
        }).toList();
    }

    @Override
    public void delete(UserProfile entity) {
        repository.delete(mapper.getUserProfileMapper().toEntity(entity));
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }

    @Override
    public List<UserProfile> findByAttribute(String attribute, Object value) {
        return List.of();
    }

    @Override
    public boolean existsById(String id) {
        return repository.existsById(id);
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public List<UserProfile> findAllById(Iterable<String> ids) {
        return repository.findAllById(ids).stream().map(userProfileEntity -> {
            UserProfile userProfile = mapper.getUserProfileMapper().toDomain(userProfileEntity);
            userProfile.setUser(mapper.getUserMapper().toDomain(userProfileEntity.getUser()));
            return userProfile;
        }).toList();
    }

    @Override
    public Page<UserProfile> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(userProfileEntity -> {
            UserProfile userProfile = mapper.getUserProfileMapper().toDomain(userProfileEntity);
            userProfile.setUser(mapper.getUserMapper().toDomain(userProfileEntity.getUser()));
            return userProfile;
        });
    }

}