package org.app.serviceusers.management.users.application.services;

import org.app.serviceusers.management.users.application.dtos.requests.CreateUserRequest;
import org.app.serviceusers.management.users.application.dtos.responses.BaseResponse;
import org.app.serviceusers.management.users.application.mappers.UserMapper;
import org.app.serviceusers.management.users.application.ports.inputs.IUserInputRepository;
import org.app.serviceusers.management.users.application.ports.outputs.IUserOutputRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserOutputRepository {

    private final IUserInputRepository inputRepository;

    private final UserMapper mapper;

    public UserServiceImpl(IUserInputRepository inputRepository, UserMapper mapper) {
        this.inputRepository = inputRepository;
        this.mapper = mapper;
    }

    @Override
    public BaseResponse create(CreateUserRequest request) {
        return BaseResponse.builder()
                .data(mapper.toDto(inputRepository.save(mapper.toDomain(request))))
                .message("User created successfully")
                .success(Boolean.TRUE)
                .status(HttpStatus.CREATED.value())
                .httpStatus(HttpStatus.CREATED).build();
    }

}