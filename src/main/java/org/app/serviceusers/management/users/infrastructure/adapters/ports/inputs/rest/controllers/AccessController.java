package org.app.serviceusers.management.users.infrastructure.adapters.ports.inputs.rest.controllers;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.app.serviceusers.management.users.application.dtos.requests.LogInRequest;
import org.app.serviceusers.management.users.application.dtos.requests.LogOutRequest;
import org.app.serviceusers.management.users.application.dtos.responses.BaseResponse;
import org.app.serviceusers.management.users.application.dtos.responses.LogInResponse;
import org.app.serviceusers.management.users.application.ports.inputs.IAccessPortInputService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("access")
public class AccessController {

    private final IAccessPortInputService service;

    public AccessController(IAccessPortInputService service) {
        this.service = service;
    }

    @PostMapping("api/v1/log-in")
    public ResponseEntity<BaseResponse> logInV2(@RequestBody @Valid LogInRequest request, HttpServletResponse response) {
        return service.logIn(request, response).apply();
    }

    @PostMapping("api/v1/log-out")
    public ResponseEntity<BaseResponse> logOut(@RequestBody @Valid LogOutRequest request) {
        return service.logOut(request).apply();
    }

}