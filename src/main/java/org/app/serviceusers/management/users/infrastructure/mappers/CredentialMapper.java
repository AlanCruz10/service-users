package org.app.serviceusers.management.users.infrastructure.mappers;

import org.app.serviceusers.management.users.domain.models.Credential;
import org.app.serviceusers.management.users.infrastructure.adapters.ports.outputs.persistance.entities.CredentialEntity;
import org.app.serviceusers.management.users.infrastructure.mappers.interfaces.IBaseMapper;
import org.springframework.stereotype.Component;

@Component("infrastructureCredentialMapper")
public class CredentialMapper implements IBaseMapper<Credential, CredentialEntity> {

    @Override
    public CredentialEntity toEntity(Credential domain) {
        CredentialEntity entity = new CredentialEntity();
        if (domain.getUuid() != null) {
            entity.setUuid(domain.getUuid());
        }
        entity.setEmail(domain.getEmail());
        entity.setPassword(domain.getPassword());
        return entity;
    }

    @Override
    public Credential toDomain(CredentialEntity entity) {
        Credential domain = new Credential();
        if (entity.getUuid() != null) {
            domain.setUuid(entity.getUuid());
        }
        domain.setEmail(entity.getEmail());
        domain.setPassword(entity.getPassword());
        return domain;
    }

}