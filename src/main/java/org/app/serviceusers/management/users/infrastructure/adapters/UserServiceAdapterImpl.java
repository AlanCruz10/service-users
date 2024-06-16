package org.app.serviceusers.management.users.infrastructure.adapters;

import org.app.serviceusers.management.users.application.ports.inputs.IUserInputRepository;
import org.app.serviceusers.management.users.domain.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserServiceAdapterImpl implements IUserInputRepository {

    @Override
    public User save(User entity) {
        return null;
    }

    @Override
    public User findById(String id) {
        return null;
    }

}