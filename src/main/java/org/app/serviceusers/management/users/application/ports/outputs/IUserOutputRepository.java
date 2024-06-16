package org.app.serviceusers.management.users.application.ports.outputs;

import org.app.serviceusers.management.users.application.dtos.requests.CreateUserRequest;
import org.app.serviceusers.management.users.application.dtos.responses.BaseResponse;

public interface IUserOutputRepository {

    BaseResponse create(CreateUserRequest request);

}