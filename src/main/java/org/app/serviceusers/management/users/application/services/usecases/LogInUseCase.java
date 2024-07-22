package org.app.serviceusers.management.users.application.services.usecases;

import io.jsonwebtoken.Jwts;
import jakarta.transaction.Transactional;
import org.app.serviceusers.management.users.application.dtos.requests.LogInRequest;
import org.app.serviceusers.management.users.application.dtos.responses.LogInResponse;
import org.app.serviceusers.management.users.application.ports.inputs.IHash256Encoder;
import org.app.serviceusers.management.users.application.ports.outputs.IAccessPortOutputService;
import org.app.serviceusers.management.users.application.ports.outputs.IUserPortOutputService;
import org.app.serviceusers.management.users.application.ports.outputs.IUserProfilePortOutputService;
import org.app.serviceusers.management.users.domain.models.Access;
import org.app.serviceusers.management.users.domain.models.User;
import org.app.serviceusers.management.users.domain.models.UserProfile;
import org.app.serviceusers.management.users.infrastructure.configurations.jwt.JwtConfiguration;
import org.app.serviceusers.management.users.infrastructure.configurations.security.user.UserDetailsImpl;
import org.app.serviceusers.management.users.infrastructure.exceptions.NotFoundException;
import org.app.serviceusers.management.users.infrastructure.exceptions.PreconditionFailedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class LogInUseCase {

    private final IUserPortOutputService port;
    private final SecretKey secretKey;
    private final AuthenticationManager authenticationManager;
    private final JwtConfiguration jwtConfiguration;
    private final IHash256Encoder hash256Encoder;

    public LogInUseCase(IUserPortOutputService port, SecretKey secretKey, AuthenticationManager authenticationManager, JwtConfiguration jwtConfiguration, IHash256Encoder hash256Encoder) {
        this.port = port;
        this.secretKey = secretKey;
        this.authenticationManager = authenticationManager;
        this.jwtConfiguration = jwtConfiguration;
        this.hash256Encoder = hash256Encoder;
    }

    @Transactional
    public LogInResponse logIn(LogInRequest request) {
        Authentication authenticate = authenticate(request.getEmail(), request.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        UserDetailsImpl principal = (UserDetailsImpl) authenticate.getPrincipal();
        String token = generateToken(principal);
        String refreshToken = generateRefreshToken(principal);
        User user = port.findByEmail(principal.getUsername()).orElseThrow(() -> new NotFoundException(principal.getUsername()));

        if (user.getUserProfile().getDate().getDeletedAt() != null) {
            throw new PreconditionFailedException("User is deleted");
        }

        boolean hasActiveSession = user.getAccesses().stream()
                .anyMatch(access -> access.getLastLogIn() != null && access.getLastLogOut() == null);

        if (hasActiveSession) {
            List<Access> accesses = new ArrayList<>(user.getAccesses());
            Optional<Access> lastAccess = accesses.stream()
                    .filter(access -> access.getLastLogIn() != null && access.getLastLogOut() == null)
                    .findFirst();
            if (lastAccess.isPresent()) {
                Access access = lastAccess.get();
                access.setLastLogOut(LocalDateTime.now());
                user.setAccesses(accesses);
                port.save(user);
            } else {
                throw new PreconditionFailedException("User is not logged in");
            }
//            throw new PreconditionFailedException("User is already logged in, close the session to log in again");
        }

        updateLastLogin(user);

        return new LogInResponse(
                principal.getUuid(),
                principal.getName(),
                jwtConfiguration.getTokenPrefix() + token,
                jwtConfiguration.getTokenPrefix() + refreshToken);

    }

    private Authentication authenticate(String email, String password) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(hash256Encoder.encodeToString(email), password);
        return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
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

    private void updateLastLogin(User user) {
        List<Access> accesses = new ArrayList<>(user.getAccesses());
        Access accessNew = new Access();
        accessNew.setLastLogIn(LocalDateTime.now());
        accessNew.setUser(user);
        accesses.add(accessNew);
        user.setAccesses(accesses);
        port.save(user);
    }

}