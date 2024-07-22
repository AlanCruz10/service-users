package org.app.serviceusers.management.users.infrastructure.adapters.ports.outputs.persistance.repositories.jpa;

import org.app.serviceusers.management.users.infrastructure.adapters.ports.outputs.persistance.entities.DateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDateJpaRepository extends JpaRepository<DateEntity, String> { }