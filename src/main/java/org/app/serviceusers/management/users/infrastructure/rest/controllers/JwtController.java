package org.app.serviceusers.management.users.infrastructure.rest.controllers;

import org.app.serviceusers.management.users.application.dtos.requests.CreateUserRequest;
import org.app.serviceusers.management.users.application.dtos.requests.RefreshJwtRequest;
import org.app.serviceusers.management.users.application.dtos.responses.BaseResponse;
import org.app.serviceusers.management.users.application.ports.outputs.IJwtOutputRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("token")
public class JwtController {

    private final IJwtOutputRepository repository;

    public JwtController(IJwtOutputRepository repository) {
        this.repository = repository;
    }

    @PostMapping()
    public ResponseEntity<BaseResponse> refresh(@RequestBody RefreshJwtRequest request) {
        return repository.tokenRefresh(request).apply();
    }

}