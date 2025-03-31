package com.ecsolutions.cadastros.configuration.security;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, KeycloakAuthenticationManager manager) throws Exception {
            http
                    .securityMatcher("/**")
                    .authorizeHttpRequests(authorizeHttpRequests ->
                            authorizeHttpRequests.requestMatchers(
                                            "/openapi/*",
                                            "/openapi/*/*",
                                            "/*/swagger-ui/*",
                                            "/*/swagger-ui.html/*",
                                            "/*/swagger-resources/*",
                                            "/v3/api-docs",
                                            "/v3/api-docs/*",
                                            "/actuator/health"
                                    ).permitAll()
                                    .requestMatchers(HttpMethod.OPTIONS).permitAll()
                                    .anyRequest().authenticated()
                    )
                    .sessionManagement((management) -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .csrf(AbstractHttpConfigurer::disable)
                    .oauth2ResourceServer(configurer -> configurer.jwt((jwt) -> jwt.authenticationManager(manager)));


        return http.build();
    }
}
