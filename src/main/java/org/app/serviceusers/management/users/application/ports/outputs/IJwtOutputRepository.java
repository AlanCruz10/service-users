package org.app.serviceusers.management.users.application.ports.outputs;

import org.app.serviceusers.management.users.application.dtos.requests.RefreshJwtRequest;
import org.app.serviceusers.management.users.application.dtos.responses.BaseResponse;

public interface IJwtOutputRepository {

    BaseResponse tokenRefresh(RefreshJwtRequest request);

}