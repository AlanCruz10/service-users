package org.app.serviceusers.management.users.infrastructure.mappers;

import org.app.serviceusers.management.users.domain.models.Access;
import org.app.serviceusers.management.users.infrastructure.adapters.ports.outputs.persistance.entities.AccessEntity;
import org.app.serviceusers.management.users.infrastructure.mappers.interfaces.IBaseMapper;
import org.springframework.stereotype.Component;

@Component("infrastructureAccessMapper")
public class AccessMapper implements IBaseMapper<Access, AccessEntity> {

    @Override
    public AccessEntity toEntity(Access domain) {
        if (domain == null) {
            return null;
        }
        AccessEntity entity = new AccessEntity();
        if (domain.getUuid() != null) {
            entity.setUuid(domain.getUuid());
        }
        entity.setLastLogIn(domain.getLastLogIn());
        entity.setLastLogOut(domain.getLastLogOut());
        return entity;
    }

    @Override
    public Access toDomain(AccessEntity entity) {
        if (entity == null) {
            return null;
        }
        Access domain = new Access();
        if (entity.getUuid() != null) {
            domain.setUuid(entity.getUuid());
        }
        domain.setLastLogIn(entity.getLastLogIn());
        domain.setLastLogOut(entity.getLastLogOut());
        return domain;
    }

}