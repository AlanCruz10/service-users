package org.app.serviceusers.management.users.domain.repositories;


public interface BaseRepository<T> {

    T save(T entity);
    T findById(String id);

}