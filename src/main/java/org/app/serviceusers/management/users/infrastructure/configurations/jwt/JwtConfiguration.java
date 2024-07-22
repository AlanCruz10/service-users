package org.app.serviceusers.management.users.infrastructure.configurations.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class JwtConfiguration {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.token-prefix}")
    private String tokenPrefix;

    @Value("${security.jwt.token-expiration}")
    private Integer tokenExpiration;

    @Value("${security.jwt.refresh-token-expiration}")
    private Integer refreshTokenExpiration;

}