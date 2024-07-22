package org.app.serviceusers.management.users.application.ports.outputs;

import org.app.serviceusers.management.users.domain.models.User;
import org.app.serviceusers.management.users.domain.repositories.ICredentialRepository;

import java.util.Optional;


public interface ICredentialPortOutputService extends ICredentialRepository {

    Optional<User> findByEmail(String email);

}