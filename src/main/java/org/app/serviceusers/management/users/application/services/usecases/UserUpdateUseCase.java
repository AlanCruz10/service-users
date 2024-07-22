package org.app.serviceusers.management.users.application.services.usecases;

import jakarta.transaction.Transactional;
import org.app.serviceusers.management.users.application.dtos.requests.UpdateUserRequest;
import org.app.serviceusers.management.users.application.dtos.responses.UserResponse;
import org.app.serviceusers.management.users.application.mappers.MapperApplicationFactory;
import org.app.serviceusers.management.users.application.ports.inputs.IHash256Encoder;
import org.app.serviceusers.management.users.application.ports.inputs.IUserProfilePortInputService;
import org.app.serviceusers.management.users.application.ports.outputs.IUserPortOutputService;
import org.app.serviceusers.management.users.application.ports.outputs.IUserProfilePortOutputService;
import org.app.serviceusers.management.users.domain.models.Access;
import org.app.serviceusers.management.users.domain.models.Credential;
import org.app.serviceusers.management.users.domain.models.User;
import org.app.serviceusers.management.users.domain.models.UserProfile;
import org.app.serviceusers.management.users.infrastructure.exceptions.NotFoundException;
import org.app.serviceusers.management.users.infrastructure.exceptions.PreconditionFailedException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserUpdateUseCase {

    private final IUserPortOutputService port;
    private final IUserProfilePortOutputService userProfilePort;
    private final IUserProfilePortInputService userProfilePortInput;
    private final PasswordEncoder passwordEncoder;
    private final IHash256Encoder hash256Encoder;
    private final MapperApplicationFactory mapper;

    public UserUpdateUseCase(IUserPortOutputService port, IUserProfilePortOutputService userProfilePort, IUserProfilePortInputService userProfilePortInput, PasswordEncoder passwordEncoder, IHash256Encoder hash256Encoder, MapperApplicationFactory mapper) {
        this.port = port;
        this.userProfilePort = userProfilePort;
        this.userProfilePortInput = userProfilePortInput;
        this.passwordEncoder = passwordEncoder;
        this.hash256Encoder = hash256Encoder;
        this.mapper = mapper;
    }

    @Transactional
    public UserResponse update(UpdateUserRequest request, MultipartFile profilePicture) {

        UserProfile userProfile = userProfilePort.findById(request.getUuid()).orElseThrow(() -> new NotFoundException(request.getUuid()));

        if (userProfile.getDate().getDeletedAt() != null) {
            throw new NotFoundException("User is deleted");
        }

        User user = userProfile.getUser();

        Credential credential = user.getCredential();

        if (!request.getUsername().isBlank() || !request.getUsername().isEmpty()) {
            userProfile.setUsername(request.getUsername());
        }
        if (!request.getPassword().isBlank() || !request.getPassword().isEmpty() && !request.getPasswordConfirmation().isBlank() || !request.getPasswordConfirmation().isEmpty() ) {
            if (request.getPassword().equals(request.getPasswordConfirmation())) {
                if (passwordEncoder.matches(request.getPassword(), credential.getPassword())) {
                    throw new PreconditionFailedException("Password is the same as the current password");
                }else {
                    credential.setPassword(passwordEncoder.encode(request.getPassword()));
                }
            }else {
                throw new PreconditionFailedException("Passwords do not match");
            }
        }
        if (!request.getEmail().isBlank() || !request.getEmail().isEmpty()) {
            if (!hash256Encoder.matches(request.getEmail(), credential.getEmail())) {
                credential.setEmail(hash256Encoder.encodeToString(request.getEmail()));
                List<Access> accesses = new ArrayList<>(user.getAccesses());
                Optional<Access> lastAccess = accesses.stream()
                        .filter(access -> access.getLastLogIn() != null && access.getLastLogOut() == null)
                        .findFirst();
                if (lastAccess.isPresent()) {
                    Access access = lastAccess.get();
                    access.setLastLogOut(LocalDateTime.now());
                    user.setAccesses(accesses);
                } else {
                    throw new PreconditionFailedException("User is not logged in");
                }
            }else {
                throw new PreconditionFailedException("Email is already in use");
            }
        }
        if (profilePicture != null) {
            userProfile.setProfilePicture(userProfilePortInput.uploadProfilePicture(userProfile, profilePicture));
        }
        if (!request.getCity().isBlank() || !request.getCity().isEmpty()) {
            userProfile.setCity(request.getCity());
        }
        if (request.getBirthDate() != null) {
            userProfile.setBirthDate(request.getBirthDate());
        }

        userProfile.getDate().setUpdatedAt(LocalDateTime.now());

        user.setCredential(credential);
        userProfile.setUser(user);
        user.setUserProfile(userProfile);

        try {
            return mapper.getUserMapper().toDto(port.save(user));
        } catch (Exception e) {
            throw new DataIntegrityViolationException("User already exists");
        }

    }

}