package org.app.serviceusers.management.users.infrastructure.rest.controllers;

import org.app.serviceusers.management.users.application.dtos.requests.CreateUserRequest;
import org.app.serviceusers.management.users.application.dtos.responses.BaseResponse;
import org.app.serviceusers.management.users.application.ports.outputs.IUserOutputRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

    private final IUserOutputRepository service;

    public UserController(IUserOutputRepository service) {
        this.service = service;
    }

    @PostMapping()
    public ResponseEntity<BaseResponse> create(@RequestBody CreateUserRequest request) {
        return service.create(request).apply();
    }

}