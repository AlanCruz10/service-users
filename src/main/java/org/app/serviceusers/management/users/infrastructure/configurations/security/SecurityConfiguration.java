package org.app.serviceusers.management.users.infrastructure.configurations.security;

import io.jsonwebtoken.security.Keys;
import org.app.serviceusers.management.users.infrastructure.configurations.jwt.JwtConfiguration;
import org.app.serviceusers.management.users.infrastructure.configurations.security.filters.JwtAuthenticationFilter;
import org.app.serviceusers.management.users.infrastructure.configurations.security.filters.JwtAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.SecretKey;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final AuthenticationManager authenticationManager;

//    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    private final JwtConfiguration jwtConfiguration;

    public SecurityConfiguration(AuthenticationManager authenticationManager, JwtConfiguration jwtConfiguration) {
//        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
        this.authenticationManager = authenticationManager;
//        this.secretKey = secretKey;
        this.jwtConfiguration = jwtConfiguration;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        configurationCors(httpSecurity);
        configurationCsrf(httpSecurity);
        configureSessionManagement(httpSecurity);
        configureRequestAuthorization(httpSecurity);
        configureFilters(httpSecurity);
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
                .requestMatchers("/user/**").permitAll()
                .anyRequest().authenticated());
    }

    private void configureFilters(HttpSecurity http) {
        JwtAuthenticationFilter userAuthenticationFilter = new JwtAuthenticationFilter(secretKey(), jwtConfiguration);
        userAuthenticationFilter.setAuthenticationManager(authenticationManager);
        userAuthenticationFilter.setFilterProcessesUrl("/login");

        http.addFilter(userAuthenticationFilter)
                .addFilterBefore(jwtAuthorizationFilter(), JwtAuthenticationFilter.class);
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(secretKey());
    }

    @Bean
    public SecretKey secretKey() {
        return Keys.hmacShaKeyFor(jwtConfiguration.getSecretKey().getBytes());
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

}