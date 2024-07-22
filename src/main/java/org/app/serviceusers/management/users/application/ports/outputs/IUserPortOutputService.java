package org.app.serviceusers.management.users.application.ports.outputs;

import jakarta.validation.constraints.NotNull;
import org.app.serviceusers.management.users.domain.models.User;
import org.app.serviceusers.management.users.domain.repositories.IUserRepository;

import java.util.Optional;

public interface IUserPortOutputService extends IUserRepository {

    Optional<User> findByEmail(@NotNull String email);

}