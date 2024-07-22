package org.app.serviceusers.management.users.infrastructure.mappers;

import org.app.serviceusers.management.users.domain.valueobjects.Date;
import org.app.serviceusers.management.users.infrastructure.adapters.ports.outputs.persistance.entities.DateEntity;
import org.app.serviceusers.management.users.infrastructure.mappers.interfaces.IBaseMapper;
import org.springframework.stereotype.Component;

@Component("infrastructureDateMapper")
public class DateMapper implements IBaseMapper<Date, DateEntity> {

    @Override
    public DateEntity toEntity(Date domain) {
        DateEntity entity = new DateEntity();
        if (domain.getUuid() != null) {
            entity.setUuid(domain.getUuid());
        }
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        entity.setDeletedAt(domain.getDeletedAt());
        return entity;
    }

    @Override
    public Date toDomain(DateEntity entity) {
        Date domain = new Date();
        if (entity.getUuid() != null) {
            domain.setUuid(entity.getUuid());
        }
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        domain.setDeletedAt(entity.getDeletedAt());
        return domain;
    }

}