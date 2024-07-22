package org.app.serviceusers.management.users.infrastructure.mappers.interfaces;

public interface IBaseMapper<D, E> {

    E toEntity(D domain);

    D toDomain(E entity);

}