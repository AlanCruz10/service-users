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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

        try {
            UserAuth userAuth = new ObjectMapper().readValue(request.getReader(), UserAuth.class);
            UsernamePasswordAuthenticationToken usernamePAT = new UsernamePasswordAuthenticationToken(
                    userAuth.getUsername(),
                    userAuth.getPassword());
            return getAuthenticationManager().authenticate(usernamePAT);
        } catch (IOException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authentication) throws IOException, ServletException {

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        Date expirationDate = Date.valueOf(LocalDate.now().plusDays(jwtConfiguration.getTokenExpiration()));

        String token = Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .claim("authorities", userPrincipal.getAuthorities())
                .setIssuedAt(new java.util.Date())
                .setExpiration(expirationDate)
                .signWith(secretKey)
                .compact();

        Date refreshTokenExpirationDate = Date.valueOf(LocalDate.now().plusDays(jwtConfiguration.getRefreshTokenExpiration()));

        String refreshToken = Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new java.util.Date())
                .setExpiration(refreshTokenExpirationDate)
                .signWith(secretKey).compact();

        response.addHeader("Authorization", jwtConfiguration.getTokenPrefix() + token);

        JwtResponse jwtResponse = JwtResponse.builder().accessJwt(null).refreshJwt(refreshToken).build();

        BaseResponse baseResponse = BaseResponse.builder()
                .data(jwtResponse)
                .message("Successfully authenticated")
                .success(Boolean.TRUE)
                .status(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK).build();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(new ObjectMapper().writeValueAsString(baseResponse));
        response.getWriter().flush();

        super.successfulAuthentication(request, response, chain, authentication);

    }

}