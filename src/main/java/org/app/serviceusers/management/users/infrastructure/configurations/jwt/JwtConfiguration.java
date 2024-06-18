package org.app.serviceusers.management.users.infrastructure.configurations.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "security.jwt")
public class JwtConfiguration {

    private String secretKey;
    private String tokenPrefix;
    private Integer tokenExpiration;
    private Integer refreshTokenExpiration;

}