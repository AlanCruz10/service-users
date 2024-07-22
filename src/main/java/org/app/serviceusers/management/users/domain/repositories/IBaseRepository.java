package org.app.serviceusers.management.users.domain.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IBaseRepository<T, UUID> {

    T save(T entity);

    Optional<T> findById(UUID id);

    List<T> findAll();

    void delete(T entity);

    void deleteById(UUID id);

    List<T> findByAttribute(String attribute, Object value);

    boolean existsById(UUID id);

    long count();

    List<T> findAllById(Iterable<UUID> ids);

    Page<T> findAll(Pageable pageable);

}