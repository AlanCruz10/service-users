package org.app.serviceusers.management.users.application.services.usecases;

import jakarta.transaction.Transactional;
import org.app.serviceusers.management.users.application.dtos.requests.DeleteUserRequest;
import org.app.serviceusers.management.users.application.ports.outputs.IUserPortOutputService;
import org.app.serviceusers.management.users.application.ports.outputs.IUserProfilePortOutputService;
import org.app.serviceusers.management.users.domain.models.User;
import org.app.serviceusers.management.users.domain.models.UserProfile;
import org.app.serviceusers.management.users.domain.valueobjects.Date;
import org.app.serviceusers.management.users.infrastructure.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static net.sf.jsqlparser.util.validation.metadata.NamedObject.user;

@Service
public class UserDeleteUseCase {

    private final IUserProfilePortOutputService port;

    private final IUserPortOutputService userPort;

    public UserDeleteUseCase(IUserProfilePortOutputService port, IUserPortOutputService userPort) {
        this.port = port;
        this.userPort = userPort;
    }

    @Transactional
    public void delete(DeleteUserRequest request) {
        UserProfile userProfile = port.findById(request.getUuid()).orElseThrow(() -> new NotFoundException(request.getUuid()));

        if (userProfile.getDate().getDeletedAt() != null) {
            throw new NotFoundException(request.getUuid());
        }

        Date date = userProfile.getDate();
        date.setDeletedAt(LocalDateTime.now());
        userProfile.setDate(date);
        User user = userProfile.getUser();
        userProfile.setUser(user);
        user.setUserProfile(userProfile);
        userPort.save(user);
    }

}