package org.app.serviceusers.management.users.infrastructure.adapters.ports.outputs.services;

import jakarta.transaction.Transactional;
import org.app.serviceusers.management.users.application.ports.outputs.IUserPortOutputService;
import org.app.serviceusers.management.users.domain.models.User;
import org.app.serviceusers.management.users.infrastructure.mappers.*;
import org.app.serviceusers.management.users.infrastructure.adapters.ports.outputs.persistance.repositories.jpa.IUserJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserPortServiceAdapterImpl implements IUserPortOutputService {

    private final IUserJpaRepository repository;
    private final MapperInfrastructureFactory mapper;

    public UserPortServiceAdapterImpl(IUserJpaRepository repository, MapperInfrastructureFactory mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findUserEntityByCredential_Email(email).map(mapper.getUserMapper()::toDomain);
    }

    @Override
    @Transactional
    public User save(User entity) {
        UserMapper userMapper = mapper.getUserMapper();
        return userMapper.toDomain(repository.save(userMapper.toEntity(entity)));
    }

    @Override
    public Optional<User> findById(String id) {
        return repository.findById(id).map(mapper.getUserMapper()::toDomain);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll().stream().map(mapper.getUserMapper()::toDomain).toList();
    }

    @Override
    public void delete(User entity) {
        repository.delete(mapper.getUserMapper().toEntity(entity));
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }

    @Override
    public List<User> findByAttribute(String attribute, Object value) {
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
    public List<User> findAllById(Iterable<String> ids) {
        return repository.findAllById(ids).stream().map(mapper.getUserMapper()::toDomain).toList();
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper.getUserMapper()::toDomain);
    }

}