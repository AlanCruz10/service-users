package org.app.serviceusers.management.users.application.ports.inputs;

import jakarta.servlet.http.HttpServletResponse;
import org.app.serviceusers.management.users.application.dtos.requests.LogInRequest;
import org.app.serviceusers.management.users.application.dtos.requests.LogOutRequest;
import org.app.serviceusers.management.users.application.dtos.responses.BaseResponse;
import org.app.serviceusers.management.users.application.dtos.responses.LogInResponse;

public interface IAccessPortInputService {

    BaseResponse logIn(LogInRequest request, HttpServletResponse response);

    BaseResponse logOut(LogOutRequest request);

}