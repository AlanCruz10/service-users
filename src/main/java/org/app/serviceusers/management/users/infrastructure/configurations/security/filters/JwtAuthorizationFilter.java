package org.app.serviceusers.management.users.infrastructure.configurations.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.app.serviceusers.management.users.application.dtos.responses.BaseResponse;
import org.app.serviceusers.management.users.application.ports.outputs.ICredentialPortOutputService;
import org.app.serviceusers.management.users.domain.models.Access;
import org.app.serviceusers.management.users.domain.models.User;
import org.app.serviceusers.management.users.infrastructure.exceptions.ExpiredOrInvalidJwtException;
import org.app.serviceusers.management.users.infrastructure.exceptions.NotFoundException;
import org.app.serviceusers.management.users.infrastructure.exceptions.PreconditionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final SecretKey secretKey;

    private final ICredentialPortOutputService credentialPortOutputService;

    public JwtAuthorizationFilter(SecretKey secretKey, ICredentialPortOutputService credentialPortOutputService) {
        this.secretKey = secretKey;
        this.credentialPortOutputService = credentialPortOutputService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String bearerToken = request.getHeader("Authorization");
            if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
                String token = bearerToken.replace("Bearer ", "");
                UsernamePasswordAuthenticationToken authentication = getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException |
                 AccessDeniedException | ExpiredOrInvalidJwtException e) {
            BaseResponse baseResponse = BaseResponse.builder()
                    .message("Token error: " + e.getMessage())
                    .success(Boolean.FALSE)
                    .httpStatus(HttpStatus.UNAUTHORIZED)
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .build();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getWriter(), baseResponse);
            throw new ExpiredOrInvalidJwtException(e.getLocalizedMessage());
        }
        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        String username = claims.getSubject();

        User user = credentialPortOutputService.findByEmail(username).orElseThrow(() -> new AccessDeniedException("invalid token, user is not exist"));
        if (user.getUserProfile().getDate().getDeletedAt() != null) {
            throw new ExpiredOrInvalidJwtException("invalid token, user is not exist");
        }

        boolean hasActiveSession = user.getAccesses().stream()
                .anyMatch(access -> access.getLastLogIn() != null && access.getLastLogOut() == null);

        if (!hasActiveSession) {
            throw new ExpiredOrInvalidJwtException("invalid token, user is not logged in");
        }

        if (claims.getExpiration().before(new Date())) {
            throw new ExpiredOrInvalidJwtException("invalid token, expired");
        }

        return new UsernamePasswordAuthenticationToken(username,null, null);
    }

}