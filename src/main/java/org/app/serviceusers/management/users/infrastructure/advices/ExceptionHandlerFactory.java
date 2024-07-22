package org.app.serviceusers.management.users.infrastructure.advices;

import org.app.serviceusers.management.users.application.dtos.responses.BaseResponse;
import org.app.serviceusers.management.users.infrastructure.exceptions.ExpiredOrInvalidJwtException;
import org.app.serviceusers.management.users.infrastructure.exceptions.NotFoundException;
import org.app.serviceusers.management.users.infrastructure.exceptions.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerFactory {

    @ExceptionHandler(NotFoundException.class)
    private ResponseEntity<BaseResponse> handleNotFoundException(NotFoundException exception) {
        return BaseResponse.builder()
                .message(exception.getLocalizedMessage())
                .success(Boolean.FALSE)
                .httpStatus(HttpStatus.NOT_FOUND)
                .status(404)
                .build().apply();
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    private ResponseEntity<BaseResponse> handleDataIntegrityViolationException(DataIntegrityViolationException exception) {
        return BaseResponse.builder()
                .message(exception.getMostSpecificCause().getLocalizedMessage())
                .success(false)
                .httpStatus(HttpStatus.CONFLICT)
                .status(HttpStatus.CONFLICT.value())
                .build().apply();
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<BaseResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        return BaseResponse.builder()
                .message(exception.getLocalizedMessage())
                .success(Boolean.FALSE)
                .httpStatus(HttpStatus.METHOD_NOT_ALLOWED)
                .status(HttpStatus.METHOD_NOT_ALLOWED.value())
                .build().apply();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<BaseResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return BaseResponse.builder()
                .message(ex.getMostSpecificCause().getLocalizedMessage())
                .success(Boolean.FALSE)
                .httpStatus(HttpStatus.BAD_REQUEST)
                .status(HttpStatus.BAD_REQUEST.value())
                .build().apply();
    }

    @ExceptionHandler(ExpiredOrInvalidJwtException.class)
    public ResponseEntity<BaseResponse> handleExpiredOrInvalidJwtException(ExpiredOrInvalidJwtException ex) {
        return BaseResponse.builder()
                .message(ex.getLocalizedMessage())
                .success(Boolean.FALSE)
                .httpStatus(HttpStatus.UNAUTHORIZED)
                .status(HttpStatus.UNAUTHORIZED.value())
                .build().apply();
    }

    @ExceptionHandler(CryptographyUnavailableException.class)
    public ResponseEntity<BaseResponse> handleCryptographyUnavailableException(CryptographyUnavailableException ex) {
        return BaseResponse.builder()
                .message(ex.getLocalizedMessage())
                .success(Boolean.FALSE)
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build().apply();
    }

    @ExceptionHandler(PreconditionFailedException.class)
    public ResponseEntity<BaseResponse> handlePreconditionFailedException(PreconditionFailedException ex) {
        return BaseResponse.builder()
                .message(ex.getLocalizedMessage())
                .success(Boolean.FALSE)
                .httpStatus(HttpStatus.PRECONDITION_FAILED)
                .status(HttpStatus.PRECONDITION_FAILED.value())
                .build().apply();
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<BaseResponse> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex) {
        return BaseResponse.builder()
                .message(ex.getLocalizedMessage())
                .success(Boolean.FALSE)
                .httpStatus(HttpStatus.BAD_REQUEST)
                .status(HttpStatus.BAD_REQUEST.value())
                .build().apply();
    }

}