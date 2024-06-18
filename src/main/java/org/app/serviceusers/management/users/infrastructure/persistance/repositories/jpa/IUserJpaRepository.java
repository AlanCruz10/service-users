package org.app.serviceusers.management.users.infrastructure.persistance.repositories.jpa;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.app.serviceusers.management.users.infrastructure.persistance.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserJpaRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findByEmail(@NotBlank @Email String email);

}