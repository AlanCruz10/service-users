package org.app.serviceusers.management.users.application.ports.inputs;

import org.app.serviceusers.management.users.domain.models.User;
import org.app.serviceusers.management.users.domain.repositories.IUserRepository;

public interface IUserInputRepository extends IUserRepository {

    User findByEmail(String email);

}