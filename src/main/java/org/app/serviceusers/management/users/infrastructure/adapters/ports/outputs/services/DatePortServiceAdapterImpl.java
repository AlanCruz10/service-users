package org.app.serviceusers.management.users.infrastructure.adapters.ports.outputs.services;

import org.app.serviceusers.management.users.application.ports.outputs.IDatePortOutputService;
import org.app.serviceusers.management.users.domain.models.UserProfile;
import org.app.serviceusers.management.users.domain.valueobjects.Date;
import org.app.serviceusers.management.users.infrastructure.adapters.ports.outputs.persistance.entities.DateEntity;
import org.app.serviceusers.management.users.infrastructure.adapters.ports.outputs.persistance.repositories.jpa.IDateJpaRepository;
import org.app.serviceusers.management.users.infrastructure.mappers.DateMapper;
import org.app.serviceusers.management.users.infrastructure.mappers.MapperInfrastructureFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class DatePortServiceAdapterImpl implements IDatePortOutputService {

    private final IDateJpaRepository repository;
    private final MapperInfrastructureFactory mapper;

    public DatePortServiceAdapterImpl(IDateJpaRepository repository, MapperInfrastructureFactory mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Date save(Date entity) {
        DateMapper dateMapper = mapper.getDateMapper();
        DateEntity save = repository.save(dateMapper.toEntity(entity));
        Date date = dateMapper.toDomain(save);
        UserProfile userProfile = mapper.getUserProfileMapper().toDomain(save.getUserProfile());
        userProfile.setUser(mapper.getUserMapper().toDomain(save.getUserProfile().getUser()));
        date.setUserProfile(userProfile);
        return date;
    }

    @Override
    public Optional<Date> findById(String id) {
        return repository.findById(id).map(dateEntity -> {
            Date date = mapper.getDateMapper().toDomain(dateEntity);
            UserProfile userProfile = mapper.getUserProfileMapper().toDomain(dateEntity.getUserProfile());
            userProfile.setUser(mapper.getUserMapper().toDomain(dateEntity.getUserProfile().getUser()));
            date.setUserProfile(userProfile);
            return date;
        });
    }

    @Override
    public List<Date> findAll() {
        return repository.findAll().stream().map(dateEntity -> {
            Date date = mapper.getDateMapper().toDomain(dateEntity);
            UserProfile userProfile = mapper.getUserProfileMapper().toDomain(dateEntity.getUserProfile());
            userProfile.setUser(mapper.getUserMapper().toDomain(dateEntity.getUserProfile().getUser()));
            date.setUserProfile(userProfile);
            return date;
        }).toList();
    }

    @Override
    public void delete(Date entity) {
        repository.delete(mapper.getDateMapper().toEntity(entity));
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }

    @Override
    public List<Date> findByAttribute(String attribute, Object value) {
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
    public List<Date> findAllById(Iterable<String> ids) {
        return repository.findAllById(ids).stream().map(dateEntity -> {
            Date date = mapper.getDateMapper().toDomain(dateEntity);
            UserProfile userProfile = mapper.getUserProfileMapper().toDomain(dateEntity.getUserProfile());
            userProfile.setUser(mapper.getUserMapper().toDomain(dateEntity.getUserProfile().getUser()));
            date.setUserProfile(userProfile);
            return date;
        }).toList();
    }

    @Override
    public Page<Date> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(dateEntity -> {
            Date date = mapper.getDateMapper().toDomain(dateEntity);
            UserProfile userProfile = mapper.getUserProfileMapper().toDomain(dateEntity.getUserProfile());
            userProfile.setUser(mapper.getUserMapper().toDomain(dateEntity.getUserProfile().getUser()));
            date.setUserProfile(userProfile);
            return date;
        });
    }

}