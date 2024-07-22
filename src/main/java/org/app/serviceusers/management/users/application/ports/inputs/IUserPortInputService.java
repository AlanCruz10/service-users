package org.app.serviceusers.management.users.application.ports.inputs;

import org.app.serviceusers.management.users.application.dtos.requests.CreateUserRequest;
import org.app.serviceusers.management.users.application.dtos.requests.DeleteUserRequest;
import org.app.serviceusers.management.users.application.dtos.requests.GetUserRequest;
import org.app.serviceusers.management.users.application.dtos.requests.UpdateUserRequest;
import org.app.serviceusers.management.users.application.dtos.responses.BaseResponse;
import org.springframework.web.multipart.MultipartFile;

public interface IUserPortInputService {

    BaseResponse get(GetUserRequest request);

    BaseResponse create(CreateUserRequest request);

    BaseResponse update(UpdateUserRequest request, MultipartFile profilePicture);

    BaseResponse delete(DeleteUserRequest request);

}