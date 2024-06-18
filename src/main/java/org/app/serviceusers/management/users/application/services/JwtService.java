package org.app.serviceusers.management.users.application.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.app.serviceusers.management.users.application.dtos.requests.RefreshJwtRequest;
import org.app.serviceusers.management.users.application.dtos.responses.BaseResponse;
import org.app.serviceusers.management.users.application.dtos.responses.JwtResponse;
import org.app.serviceusers.management.users.application.ports.outputs.IJwtOutputRepository;
import org.app.serviceusers.management.users.infrastructure.configurations.jwt.JwtConfiguration;
import org.app.serviceusers.management.users.infrastructure.configurations.security.user.UserDetailsImpl;
import org.app.serviceusers.management.users.infrastructure.configurations.security.user.UserDetailsServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.sql.Date;
import java.time.LocalDate;

@Service
public class JwtService implements IJwtOutputRepository {

    private final JwtConfiguration jwtConfig;

    private final SecretKey secretKey;

    private final UserDetailsServiceImpl userDetailsService;

    public JwtService(JwtConfiguration jwtConfig, SecretKey secretKey, UserDetailsServiceImpl userDetailsService) {
        this.jwtConfig = jwtConfig;
        this.secretKey = secretKey;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public BaseResponse tokenRefresh(RefreshJwtRequest refreshToken) {

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(refreshToken.getRefreshToken())
                .getBody();
        String email = claims.getSubject();

        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(email);

        Date expirationDate = Date.valueOf(LocalDate.now().plusDays(jwtConfig.getTokenExpiration()));
        String token = Jwts.builder()
                .setSubject(userDetails.getName())
                .claim("authorities", userDetails.getAuthorities())
                .setIssuedAt(new java.util.Date())
                .setExpiration(expirationDate)
                .signWith(secretKey).compact();

        Date refreshExpirationDate = Date.valueOf(LocalDate.now().plusDays(jwtConfig.getRefreshTokenExpiration()));
        String newRefreshToken = Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new java.util.Date())
                .setExpiration(refreshExpirationDate)
                .signWith(secretKey).compact();

        JwtResponse jwtResponse = JwtResponse.builder()
                .refreshJwt(newRefreshToken)
                .accessJwt(token).build();

        return BaseResponse.builder()
                .data(jwtResponse)
                .message("Successfully authenticated")
                .success(Boolean.TRUE)
                .status(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK).build();

    }

}