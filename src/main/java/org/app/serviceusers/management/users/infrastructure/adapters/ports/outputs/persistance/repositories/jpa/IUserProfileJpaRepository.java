package org.app.serviceusers.management.users.infrastructure.adapters.ports.outputs.persistance.repositories.jpa;

import org.app.serviceusers.management.users.domain.models.UserProfile;
import org.app.serviceusers.management.users.infrastructure.adapters.ports.outputs.persistance.entities.UserProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserProfileJpaRepository extends JpaRepository<UserProfileEntity, String> {

    Optional<UserProfileEntity> findByUuid(String id);

}