package org.app.serviceusers.management.users.infrastructure.configurations.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.app.serviceusers.management.users.application.dtos.responses.BaseResponse;
import org.app.serviceusers.management.users.application.dtos.responses.JwtResponse;
import org.app.serviceusers.management.users.infrastructure.configurations.jwt.JwtConfiguration;
import org.app.serviceusers.management.users.infrastructure.configurations.security.user.UserAuth;
import org.app.serviceusers.management.users.infrastructure.configurations.security.user.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final SecretKey secretKey;

    private final JwtConfiguration jwtConfiguration;

    public JwtAuthenticationFilter(SecretKey secretKey, JwtConfiguration jwtConfiguration) {
        this.secretKey = secretKey;
        this.jwtConfiguration = jwtConfiguration;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Method not supported: " + request.getMethod());
        }

        UserAuth userAuth = null;
        try {
            userAuth = new ObjectMapper().readValue(request.getReader(), UserAuth.class);
        } catch (IOException e) {
            throw new AuthenticationServiceException("Invalid request body");
        }

        UsernamePasswordAuthenticationToken usernamePAT = new UsernamePasswordAuthenticationToken(
                userAuth.getEmail(),
                userAuth.getPassword());
        return getAuthenticationManager().authenticate(usernamePAT);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authentication) throws IOException, ServletException {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        String token = generateToken(userPrincipal);

        String refreshToken = generateRefreshToken(userPrincipal);

        response.addHeader("Authorization", jwtConfiguration.getTokenPrefix() + token);
        response.addHeader("Refresh-Token", jwtConfiguration.getTokenPrefix() + refreshToken);

        BaseResponse baseResponse = BaseResponse.builder()
                .data(new JwtResponse(
                        userPrincipal.getUuid(),
                        userPrincipal.getName(),
                        jwtConfiguration.getTokenPrefix() + token,
                        null))
                .message("Successfully authenticated")
                .success(Boolean.TRUE)
                .status(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK).build();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(new ObjectMapper().writeValueAsString(baseResponse));
        response.getWriter().flush();

        super.successfulAuthentication(request, response, chain, authentication);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        BaseResponse baseResponse = BaseResponse.builder()
                .message("Failed to authenticate. " + failed.getLocalizedMessage())
                .success(Boolean.FALSE)
                .status(HttpStatus.UNAUTHORIZED.value())
                .httpStatus(HttpStatus.UNAUTHORIZED).build();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(new ObjectMapper().writeValueAsString(baseResponse));
        response.getWriter().flush();
    }

    private String generateToken(UserDetailsImpl user) {
        Date expirationDate = Date.valueOf(LocalDate.now().plusDays(jwtConfiguration.getTokenExpiration()));
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("authorities", user.getAuthorities())
                .setIssuedAt(new java.util.Date())
                .setExpiration(expirationDate)
                .signWith(secretKey)
                .compact();
    }

    private String generateRefreshToken(UserDetailsImpl user) {
        Date refreshExpirationDate = Date.valueOf(LocalDate.now().plusDays(jwtConfiguration.getRefreshTokenExpiration()));
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new java.util.Date())
                .setExpiration(refreshExpirationDate)
                .signWith(secretKey)
                .compact();
    }

}