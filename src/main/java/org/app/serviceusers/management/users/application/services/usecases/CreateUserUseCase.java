package org.app.serviceusers.management.users.application.services.usecases;

import jakarta.transaction.Transactional;
import org.app.serviceusers.management.users.application.dtos.requests.CreateUserRequest;
import org.app.serviceusers.management.users.application.dtos.responses.UserResponse;
import org.app.serviceusers.management.users.application.mappers.MapperApplicationFactory;
import org.app.serviceusers.management.users.application.mappers.UserMapper;
import org.app.serviceusers.management.users.application.ports.inputs.IHash256Encoder;
import org.app.serviceusers.management.users.application.ports.outputs.ICredentialPortOutputService;
import org.app.serviceusers.management.users.application.ports.outputs.IUserPortOutputService;
import org.app.serviceusers.management.users.domain.models.User;
import org.app.serviceusers.management.users.infrastructure.exceptions.PreconditionFailedException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreateUserUseCase {

    private final IUserPortOutputService port;
    private final ICredentialPortOutputService credentialPortOutputService;
    private final MapperApplicationFactory mapperApplicationFactory;
    private final IHash256Encoder hash256Encoder;
    private final PasswordEncoder passwordEncoder;

    CreateUserUseCase(IUserPortOutputService port, ICredentialPortOutputService credentialPortOutputService, IHash256Encoder hash256Encoder, PasswordEncoder passwordEncoder, MapperApplicationFactory mapperApplicationFactory) {
        this.port = port;
        this.credentialPortOutputService = credentialPortOutputService;
        this.mapperApplicationFactory = mapperApplicationFactory;
        this.hash256Encoder = hash256Encoder;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserResponse create(CreateUserRequest request) {

        Optional<User> user = credentialPortOutputService.findByEmail(hash256Encoder.encodeToString(request.getEmail()));

        if (user.isPresent()) {
            if (user.get().getUserProfile().getDate().getDeletedAt() != null) {
                port.deleteById(user.get().getUuid());
            } else if (hash256Encoder.matches(request.getEmail(), user.get().getCredential().getEmail())) {
                throw new DataIntegrityViolationException("User already exists");
            }else {
                throw new DataIntegrityViolationException("User already exists");
            }
        }

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new PreconditionFailedException("Passwords do not match");
        }

        UserMapper userMapper = mapperApplicationFactory.getUserMapper();
        request.setEmail(hash256Encoder.encodeToString(request.getEmail()));
        request.setPassword(passwordEncoder.encode(request.getPassword()));

        User userMapperDomain = userMapper.toDomain(request);

        try {
            return userMapper.toDto(port.save(userMapperDomain));
        } catch (Exception e) {
            throw new DataIntegrityViolationException("User already exists");
        }

    }

}
