package org.app.serviceusers.management.users.infrastructure.adapters.ports.inputs.rest.controllers;

import jakarta.validation.Valid;
import org.app.serviceusers.management.users.application.dtos.requests.RefreshJwtRequest;
import org.app.serviceusers.management.users.application.dtos.responses.BaseResponse;
import org.app.serviceusers.management.users.application.ports.outputs.IJwtOutputRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jwt")
public class JwtController {

    private final IJwtOutputRepository repository;

    public JwtController(IJwtOutputRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/api/v1/refresh")
    public ResponseEntity<BaseResponse> refresh(@RequestBody @Valid RefreshJwtRequest request) {
        return repository.tokenRefresh(request).apply();
    }

}