package org.app.serviceusers.management.users.infrastructure.adapters;

import org.app.serviceusers.management.users.application.ports.inputs.IUserInputRepository;
import org.app.serviceusers.management.users.domain.models.User;
import org.app.serviceusers.management.users.infrastructure.mappers.UserEntityMapper;
import org.app.serviceusers.management.users.infrastructure.persistance.repositories.jpa.IUserJpaRepository;
import org.springframework.stereotype.Component;

@Component
public class UserServiceAdapterImpl implements IUserInputRepository {

    private final IUserJpaRepository repository;

    private final UserEntityMapper mapper;

    public UserServiceAdapterImpl(IUserJpaRepository repository, UserEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public User save(User entity) {
        return mapper.toDomain(repository.save(mapper.toEntity(entity)));
    }

    @Override
    public User findById(String id) {
        return mapper.toDomain(repository.findById(id).orElseThrow(RuntimeException::new));
    }

    @Override
    public User findByEmail(String email) {
        return mapper.toDomain(repository.findByEmail(email).orElseThrow(RuntimeException::new));
    }

}