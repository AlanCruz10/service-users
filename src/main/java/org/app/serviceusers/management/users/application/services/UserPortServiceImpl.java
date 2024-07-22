package org.app.serviceusers.management.users.application.services;

import org.app.serviceusers.management.users.application.dtos.requests.CreateUserRequest;
import org.app.serviceusers.management.users.application.dtos.requests.DeleteUserRequest;
import org.app.serviceusers.management.users.application.dtos.requests.GetUserRequest;
import org.app.serviceusers.management.users.application.dtos.requests.UpdateUserRequest;
import org.app.serviceusers.management.users.application.dtos.responses.BaseResponse;
import org.app.serviceusers.management.users.application.ports.inputs.IUserPortInputService;
import org.app.serviceusers.management.users.application.services.usecases.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserPortServiceImpl implements IUserPortInputService {

    private final GetUserUseCase getUserUseCase;
    private final UserDeleteUseCase userDeleteUseCase;
    private final UserUpdateUseCase userUpdateUseCase;
    private final CreateUserUseCase createUserUseCase;

    @Autowired
    public UserPortServiceImpl(GetUserUseCase getUserUseCase, UserDeleteUseCase userDeleteUseCase, UserUpdateUseCase userUpdateUseCase, CreateUserUseCase createUserUseCase) {
        this.getUserUseCase = getUserUseCase;
        this.userDeleteUseCase = userDeleteUseCase;
        this.userUpdateUseCase = userUpdateUseCase;
        this.createUserUseCase = createUserUseCase;
    }

    @Override
    public BaseResponse get(GetUserRequest request) {
        return BaseResponse.builder()
                .data(getUserUseCase.get(request))
                .message("User obtained successfully")
                .success(Boolean.TRUE)
                .status(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK).build();
    }

    @Override
    public BaseResponse create(CreateUserRequest request) {
        return BaseResponse.builder()
                .data(createUserUseCase.create(request))
                .message("User created successfully")
                .success(Boolean.TRUE)
                .status(HttpStatus.CREATED.value())
                .httpStatus(HttpStatus.CREATED).build();
    }

    @Override
    public BaseResponse update(UpdateUserRequest request, MultipartFile profilePicture) {
        return BaseResponse.builder()
                .data(userUpdateUseCase.update(request, profilePicture))
                .message("User updated successfully")
                .success(Boolean.TRUE)
                .status(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK).build();
    }

    @Override
    public BaseResponse delete(DeleteUserRequest request) {
        userDeleteUseCase.delete(request);
        return BaseResponse.builder()
                .message("User deleted successfully")
                .success(Boolean.TRUE)
                .status(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK).build();
    }

}