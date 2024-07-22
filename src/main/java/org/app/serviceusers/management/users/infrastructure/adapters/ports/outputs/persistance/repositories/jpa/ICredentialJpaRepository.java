package org.app.serviceusers.management.users.infrastructure.adapters.ports.outputs.persistance.repositories.jpa;

import org.app.serviceusers.management.users.infrastructure.adapters.ports.outputs.persistance.entities.CredentialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICredentialJpaRepository extends JpaRepository<CredentialEntity, String> {

    Optional<CredentialEntity> findByEmail(String email);

}