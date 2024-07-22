package org.app.serviceusers.management.users.application.services.usecases;

import jakarta.transaction.Transactional;
import org.app.serviceusers.management.users.application.dtos.requests.GetUserRequest;
import org.app.serviceusers.management.users.application.dtos.responses.UserResponse;
import org.app.serviceusers.management.users.application.mappers.MapperApplicationFactory;
import org.app.serviceusers.management.users.application.ports.outputs.IUserProfilePortOutputService;
import org.app.serviceusers.management.users.domain.models.UserProfile;
import org.app.serviceusers.management.users.infrastructure.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class GetUserUseCase {

    private final IUserProfilePortOutputService port;
    private final MapperApplicationFactory mapper;

    public GetUserUseCase(IUserProfilePortOutputService port, MapperApplicationFactory mapper) {
        this.port = port;
        this.mapper = mapper;
    }

    @Transactional
    public UserResponse get(GetUserRequest request) {

        UserProfile userProfile = port.findById(request.getUuid()).orElseThrow(() -> new NotFoundException(request.getUuid()));

        if (userProfile.getDate().getDeletedAt() != null) {
            throw new NotFoundException(userProfile.getUuid());
        }

        return mapper.getUserMapper().toDto(userProfile.getUser());

    }

}