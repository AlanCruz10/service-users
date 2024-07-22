package org.app.serviceusers.management.users.application.services.usecases;

import jakarta.transaction.Transactional;
import org.app.serviceusers.management.users.application.dtos.requests.LogOutRequest;
import org.app.serviceusers.management.users.application.ports.outputs.IUserPortOutputService;
import org.app.serviceusers.management.users.application.ports.outputs.IUserProfilePortOutputService;
import org.app.serviceusers.management.users.domain.models.Access;
import org.app.serviceusers.management.users.domain.models.User;
import org.app.serviceusers.management.users.domain.models.UserProfile;
import org.app.serviceusers.management.users.infrastructure.exceptions.NotFoundException;
import org.app.serviceusers.management.users.infrastructure.exceptions.PreconditionFailedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class LogOutUseCase {

    private final IUserPortOutputService port;
    private final IUserProfilePortOutputService userProfilePortOutputService;

    public LogOutUseCase(IUserPortOutputService port, IUserProfilePortOutputService userProfilePortOutputService) {
        this.port = port;
        this.userProfilePortOutputService = userProfilePortOutputService;
    }

    @Transactional
    public void logOut(LogOutRequest request) {
        UserProfile userProfile = userProfilePortOutputService.findById(request.getUuid()).orElseThrow(() -> new NotFoundException(request.getUuid()));
        if (userProfile.getDate().getDeletedAt() != null) {
            throw new NotFoundException("User is deleted");
        }

        boolean hasActiveSession = userProfile.getUser().getAccesses().stream()
                .anyMatch(access -> access.getLastLogIn() != null && access.getLastLogOut() == null);

        if (hasActiveSession) {
            updateLastLogOut(userProfile.getUser());
        }else {
            throw new PreconditionFailedException("Invalid token");
        }
    }

    private void updateLastLogOut(User user) {
        List<Access> accesses = new ArrayList<>(user.getAccesses());
        Optional<Access> lastAccess = accesses.stream()
                .filter(access -> access.getLastLogIn() != null && access.getLastLogOut() == null)
                .findFirst();
        if (lastAccess.isPresent()) {
            Access access = lastAccess.get();
            access.setLastLogOut(LocalDateTime.now());
            user.setAccesses(accesses);
            port.save(user);
        } else {
            throw new PreconditionFailedException("User is not logged in");
        }
    }

}