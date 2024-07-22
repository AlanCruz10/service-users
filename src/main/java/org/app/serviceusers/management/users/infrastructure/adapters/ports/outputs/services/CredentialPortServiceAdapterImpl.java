package org.app.serviceusers.management.users.infrastructure.adapters.ports.outputs.services;

import jakarta.transaction.Transactional;
import org.app.serviceusers.management.users.application.ports.outputs.ICredentialPortOutputService;
import org.app.serviceusers.management.users.domain.models.Credential;
import org.app.serviceusers.management.users.domain.models.User;
import org.app.serviceusers.management.users.infrastructure.adapters.ports.outputs.persistance.entities.CredentialEntity;
import org.app.serviceusers.management.users.infrastructure.adapters.ports.outputs.persistance.repositories.jpa.ICredentialJpaRepository;
import org.app.serviceusers.management.users.infrastructure.exceptions.PreconditionFailedException;
import org.app.serviceusers.management.users.infrastructure.mappers.CredentialMapper;
import org.app.serviceusers.management.users.infrastructure.mappers.MapperInfrastructureFactory;
import org.app.serviceusers.management.users.infrastructure.mappers.UserMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CredentialPortServiceAdapterImpl implements ICredentialPortOutputService {

    private final ICredentialJpaRepository repository;
    private final MapperInfrastructureFactory mapper;

    public CredentialPortServiceAdapterImpl(ICredentialJpaRepository repository, MapperInfrastructureFactory mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public Optional<User> findByEmail(String email) {
        UserMapper userMapper = mapper.getUserMapper();
        return repository.findByEmail(email).map(credentialEntity -> {
            Credential domain = mapper.getCredentialMapper().toDomain(credentialEntity);
            domain.setUser(userMapper.toDomain(credentialEntity.getUser()));
            return domain.getUser();
        });
    }

    @Override
    public Credential save(Credential entity) {
        CredentialMapper credentialMapper = mapper.getCredentialMapper();
        UserMapper userMapper = mapper.getUserMapper();
        CredentialEntity save = repository.save(credentialMapper.toEntity(entity));
        Credential credential = credentialMapper.toDomain(save);
        credential.setUser(userMapper.toDomain(save.getUser()));
        return credential;
    }

    @Override
    public Optional<Credential> findById(String id) {
        CredentialMapper credentialMapper = mapper.getCredentialMapper();
        return repository.findById(id).map(credentialEntity -> {
            Credential credential = credentialMapper.toDomain(credentialEntity);
            credential.setUser(mapper.getUserMapper().toDomain(credentialEntity.getUser()));
            return credential;
        });
    }

    @Override
    public List<Credential> findAll() {
        return repository.findAll().stream().map(credentialEntity -> {
            Credential credential = mapper.getCredentialMapper().toDomain(credentialEntity);
            credential.setUser(mapper.getUserMapper().toDomain(credentialEntity.getUser()));
            return credential;
        }).toList();
    }

    @Override
    public void delete(Credential entity) {
        repository.delete(mapper.getCredentialMapper().toEntity(entity));
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }

    @Override
    public List<Credential> findByAttribute(String attribute, Object value) {
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
    public List<Credential> findAllById(Iterable<String> ids) {
        return repository.findAllById(ids).stream().map(credentialEntity -> {
            Credential credential = mapper.getCredentialMapper().toDomain(credentialEntity);
            credential.setUser(mapper.getUserMapper().toDomain(credentialEntity.getUser()));
            return credential;
        }).toList();
    }

    @Override
    public Page<Credential> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(credentialEntity -> {
            Credential credential = mapper.getCredentialMapper().toDomain(credentialEntity);
            credential.setUser(mapper.getUserMapper().toDomain(credentialEntity.getUser()));
            return credential;
        });
    }

}