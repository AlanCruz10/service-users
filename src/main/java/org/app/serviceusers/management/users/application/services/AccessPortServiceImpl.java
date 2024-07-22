package org.app.serviceusers.management.users.application.services;

import jakarta.servlet.http.HttpServletResponse;
import org.app.serviceusers.management.users.application.dtos.requests.LogInRequest;
import org.app.serviceusers.management.users.application.dtos.requests.LogOutRequest;
import org.app.serviceusers.management.users.application.dtos.responses.BaseResponse;
import org.app.serviceusers.management.users.application.dtos.responses.LogInResponse;
import org.app.serviceusers.management.users.application.ports.inputs.IAccessPortInputService;
import org.app.serviceusers.management.users.application.services.usecases.LogInUseCase;
import org.app.serviceusers.management.users.application.services.usecases.LogOutUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AccessPortServiceImpl implements IAccessPortInputService {

    private final LogInUseCase logInUseCase;
    private final LogOutUseCase logOutUseCase;

    public AccessPortServiceImpl(LogInUseCase logInUseCase, LogOutUseCase logOutUseCase) {
        this.logInUseCase = logInUseCase;
        this.logOutUseCase = logOutUseCase;
    }

    @Override
    public BaseResponse logIn(LogInRequest request, HttpServletResponse response) {
        LogInResponse logInResponse = logInUseCase.logIn(request);
        response.addHeader("Authorization", logInResponse.getToken());
        response.addHeader("Refresh-Token", logInResponse.getRefreshJwt());
        return BaseResponse.builder()
                .data(logInResponse)
                .message("User logged in successfully")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK)
                .status(HttpStatus.OK.value())
                .build();
    }

    @Override
    public BaseResponse logOut(LogOutRequest request) {
        logOutUseCase.logOut(request);
        return BaseResponse.builder()
                .message("User logged out successfully")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK)
                .status(HttpStatus.OK.value())
                .build();
    }

}