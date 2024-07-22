package org.app.serviceusers.management.users.infrastructure.adapters.ports.inputs.rest.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import org.app.serviceusers.management.users.application.dtos.requests.CreateUserRequest;
import org.app.serviceusers.management.users.application.dtos.requests.DeleteUserRequest;
import org.app.serviceusers.management.users.application.dtos.requests.GetUserRequest;
import org.app.serviceusers.management.users.application.dtos.requests.UpdateUserRequest;
import org.app.serviceusers.management.users.application.dtos.responses.BaseResponse;
import org.app.serviceusers.management.users.application.ports.inputs.IUserPortInputService;
import org.app.serviceusers.management.users.infrastructure.exceptions.PreconditionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {

    private final IUserPortInputService service;
    private final Validator validator;
    private final ObjectMapper objectMapper;

    public UserController(IUserPortInputService service, Validator validator, ObjectMapper objectMapper) {
        this.service = service;
        this.validator = validator;
        this.objectMapper = objectMapper;
    }

    @GetMapping("api/v1")
    public ResponseEntity<BaseResponse> get (@RequestBody @Valid GetUserRequest request) {
        return service.get(request).apply();
    }

    @PostMapping("api/v1/create")
    public ResponseEntity<BaseResponse> create (@RequestBody @Valid CreateUserRequest request) {
        return service.create(request).apply();
    }

    @PutMapping(value = "api/v1", consumes = "multipart/form-data")
    public ResponseEntity<BaseResponse> update (@RequestPart("request") String request,
                                                @RequestPart(name = "profilePicture", required = false) MultipartFile profilePicture) {
        UpdateUserRequest requestData;
        try {
            requestData = objectMapper.readValue(request, UpdateUserRequest.class);
        } catch (IOException e) {
            throw new PreconditionFailedException(e.getMessage());
        }

        Set<ConstraintViolation<UpdateUserRequest>> violations = validator.validate(requestData);
        if (!violations.isEmpty()) {
            Map<String, String> errors = new HashMap<>();

            violations.forEach(error -> {
                String name = error.getPropertyPath().toString();
                String errorMessage = error.getMessage();
                errors.put(name, errorMessage);
            });

            BaseResponse baseResponse = BaseResponse.builder()
                    .data(errors)
                    .message("Validation failed")
                    .success(Boolean.FALSE)
                    .httpStatus(HttpStatus.PRECONDITION_FAILED)
                    .status(HttpStatus.PRECONDITION_FAILED.value()).build();

            return baseResponse.apply();
        }
        return service.update(requestData, profilePicture).apply();
    }

    @DeleteMapping("api/v1")
    public ResponseEntity<BaseResponse> delete (@RequestBody @Valid DeleteUserRequest request) {
        return service.delete(request).apply();
    }

}