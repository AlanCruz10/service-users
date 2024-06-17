package org.app.serviceusers.management.users.infrastructure.persistance.repositories.jpa;

import org.app.serviceusers.management.users.infrastructure.persistance.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserJpaRepository extends JpaRepository<UserEntity, String> { }