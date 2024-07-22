package org.app.serviceusers.management.users.infrastructure.configurations.security.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.app.serviceusers.management.users.application.dtos.responses.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component("authenticationEntryPoint")
public class FailedAuthenticationEntryPointException implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        String localizedMessage = authException.getLocalizedMessage();
        if (localizedMessage.equals("Bad credentials") || localizedMessage.equals("No value present")) {
            localizedMessage = "password or email is incorrect";
        }

        BaseResponse baseResponse = BaseResponse.builder()
                .message("Unauthorized access, " + localizedMessage)
                .success(Boolean.FALSE)
                .httpStatus(HttpStatus.UNAUTHORIZED)
                .status(HttpStatus.UNAUTHORIZED.value())
                .build();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getWriter(), baseResponse);
        response.getWriter().flush();
    }

}