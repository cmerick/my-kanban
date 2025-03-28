package com.ecsolutions.cadastros.configuration.security;

import com.ecsolutions.cadastros.model.dtos.security.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
@RequiredArgsConstructor
public class AuthenticationConfiguration {

    @Bean
    JwtDecoder jwtDecoder(OAuth2ResourceServerProperties properties) {
        return NimbusJwtDecoder.withJwkSetUri(properties.getJwt().getJwkSetUri()).build();
    }

    @Bean
    @RequestScope
    public AuthenticatedUser authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null != authentication) {
            return (AuthenticatedUser) authentication.getPrincipal();
        }
        return AuthenticatedUser.builder().build();
    }

}
