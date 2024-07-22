package org.app.serviceusers.management.users.infrastructure.configurations.security;

import io.jsonwebtoken.security.Keys;
import org.app.serviceusers.management.users.application.ports.outputs.ICredentialPortOutputService;
import org.app.serviceusers.management.users.application.ports.outputs.IUserPortOutputService;
import org.app.serviceusers.management.users.infrastructure.configurations.jwt.JwtConfiguration;
import org.app.serviceusers.management.users.infrastructure.configurations.security.exceptions.FailedAuthenticationEntryPointException;
import org.app.serviceusers.management.users.infrastructure.configurations.security.filters.JwtAuthorizationFilter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final FailedAuthenticationEntryPointException authenticationEntryPoint;

    private final JwtConfiguration jwtConfiguration;

    private final ICredentialPortOutputService credentialPortOutputService;

    public SecurityConfiguration(@Qualifier("authenticationEntryPoint") FailedAuthenticationEntryPointException authenticationEntryPoint, JwtConfiguration jwtConfiguration, ICredentialPortOutputService credentialPortOutputService) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.jwtConfiguration = jwtConfiguration;
        this.credentialPortOutputService = credentialPortOutputService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        configurationCors(httpSecurity);
        configurationCsrf(httpSecurity);
        configureSessionManagement(httpSecurity);
        configureRequestAuthorization(httpSecurity);
        configureFilters(httpSecurity);
        configurationEntryPoint(httpSecurity);
        return httpSecurity.httpBasic(Customizer.withDefaults()).build();
    }

    private void configurationCors(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors(AbstractHttpConfigurer::disable);
    }

    private void configurationCsrf(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
    }

    private void configureSessionManagement(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.sessionManagement(sessionManagement -> sessionManagement
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    }

    private void configureRequestAuthorization(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(auth -> auth
                .requestMatchers(
                        "/users/api/v1/create",
                        "/access/api/v1/log-in")
                .permitAll()
                .anyRequest()
                .authenticated());
    }

    private void configureFilters(HttpSecurity httpSecurity) {
//        JwtAuthenticationFilter userAuthenticationFilter = new JwtAuthenticationFilter(secretKey(), jwtConfiguration);
//        userAuthenticationFilter.setAuthenticationManager(authenticationManager);
//        userAuthenticationFilter.setFilterProcessesUrl("/logIn");
//        httpSecurity.addFilter(userAuthenticationFilter)
//                .addFilterBefore(jwtAuthorizationFilter(), JwtAuthenticationFilter.class);
        httpSecurity.addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    private void configurationEntryPoint(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationEntryPoint));
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(secretKey(), credentialPortOutputService);
    }

    @Bean
    public SecretKey secretKey() {
        return Keys.hmacShaKeyFor(jwtConfiguration.getSecretKey().getBytes());
    }

}