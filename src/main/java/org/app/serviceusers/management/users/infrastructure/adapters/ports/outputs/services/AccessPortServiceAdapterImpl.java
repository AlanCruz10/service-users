package org.app.serviceusers.management.users.infrastructure.adapters.ports.outputs.services;

import org.app.serviceusers.management.users.application.ports.outputs.IAccessPortOutputService;
import org.app.serviceusers.management.users.domain.models.Access;
import org.app.serviceusers.management.users.infrastructure.adapters.ports.outputs.persistance.entities.AccessEntity;
import org.app.serviceusers.management.users.infrastructure.adapters.ports.outputs.persistance.repositories.jpa.IAccessJpaRepository;
import org.app.serviceusers.management.users.infrastructure.mappers.AccessMapper;
import org.app.serviceusers.management.users.infrastructure.mappers.MapperInfrastructureFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AccessPortServiceAdapterImpl implements IAccessPortOutputService {

    private final IAccessJpaRepository repository;
    private final MapperInfrastructureFactory mapper;

    public AccessPortServiceAdapterImpl(IAccessJpaRepository repository, MapperInfrastructureFactory mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Access save(Access entity) {
        AccessMapper accessMapper = mapper.getAccessMapper();
        AccessEntity entity1 = accessMapper.toEntity(entity);
        entity1.setUser(mapper.getUserMapper().toEntity(entity.getUser()));
        AccessEntity save = repository.save(entity1);
        Access access = accessMapper.toDomain(save);
        access.setUser(mapper.getUserMapper().toDomain(save.getUser()));
        return access;
    }

    @Override
    public Optional<Access> findById(String id) {
        AccessMapper accessMapper = mapper.getAccessMapper();
        return repository.findById(id).map(accessEntity -> {
            Access access = accessMapper.toDomain(accessEntity);
            access.setUser(mapper.getUserMapper().toDomain(accessEntity.getUser()));
            return access;
        });
    }

    @Override
    public List<Access> findAll() {;
        return repository.findAll().stream().map(accessEntity -> {
            Access access = mapper.getAccessMapper().toDomain(accessEntity);
            access.setUser(mapper.getUserMapper().toDomain(accessEntity.getUser()));
            return access;
        }).toList();
    }

    @Override
    public void delete(Access entity) {
        repository.delete(mapper.getAccessMapper().toEntity(entity));
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }

    @Override
    public List<Access> findByAttribute(String attribute, Object value) {
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
    public List<Access> findAllById(Iterable<String> ids) {
        return repository.findAllById(ids).stream().map(accessEntity -> {
            Access access = mapper.getAccessMapper().toDomain(accessEntity);
            access.setUser(mapper.getUserMapper().toDomain(accessEntity.getUser()));
            return access;
        }).toList();
    }

    @Override
    public Page<Access> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(accessEntity -> {
            Access access = mapper.getAccessMapper().toDomain(accessEntity);
            access.setUser(mapper.getUserMapper().toDomain(accessEntity.getUser()));
            return access;
        });
    }

}