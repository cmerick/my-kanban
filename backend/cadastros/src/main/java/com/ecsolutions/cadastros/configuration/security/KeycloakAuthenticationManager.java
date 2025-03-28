package com.ecsolutions.cadastros.configuration.security;

import com.ecsolutions.cadastros.model.attributes.SecurityAttributes;
import com.ecsolutions.cadastros.model.dtos.security.AuthenticatedUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.keycloak.representations.UserInfo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class KeycloakAuthenticationManager implements AuthenticationManager {

    private static final String KEYCLOAK_APPLICATION_KEY = "application";

    private final SecurityAttributes attributes;

    private final ObjectMapper objectMapper;

    private final JwtDecoder jwtDecoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if(authentication instanceof BearerTokenAuthenticationToken authenticationToken) {
            try{
                var jwt = jwtDecoder.decode(authenticationToken.getToken());

                var userInfo = this.getUserInfo(jwt, objectMapper);
                var roles = roles(userInfo, attributes.getRoleMapKeys());

                var user = new AuthenticatedUser(
                        userInfo,
                        tokenType(jwt),
                        authenticationToken.getToken(),
                        realm(jwt),
                        clientId(jwt)
                );
                return new UsernamePasswordAuthenticationToken(
                        user,
                        "",
                        roles.stream().map(SimpleGrantedAuthority::new).toList()
                );
            } catch (JwtException ignored) {
                }
        }

        return authentication;
    }

    @SneakyThrows
    public UserInfo getUserInfo(Jwt jwt, ObjectMapper objectMapper) {
        String json = objectMapper.writeValueAsString(jwt.getClaims());
        return objectMapper.readValue(json, UserInfo.class);
    }


    public String tokenType(Jwt jwt) {
        String typ = (String) jwt.getClaims().get("typ");
        return typ != null ? typ : "Bearer";
    }

    public String clientId(Jwt jwt) {
        String azp = (String) jwt.getClaims().get("azp");
        return azp != null ? azp : "";
    }

    public String realm(Jwt jwt) {
        String issuer = (String) jwt.getClaims().get("issuer");
        if (issuer != null) {
            String[] split = issuer.split("/");
            return split[split.length - 1];
        }
        return "";
    }

    public Collection<String> roles(Object obj, String[] keys) {
        return switch (obj) {
            case UserInfo userInfo -> roles(userInfo, keys);
            case Map<?, ?> map -> roles(map, keys);
            case List<?> list -> roles(list);
            case null, default -> Collections.emptyList();
        };
    }

    public Collection<String> roles(UserInfo userInfo, String[] keys) {
        Set<String> roles = new HashSet<>();
        for (String key : keys) {
            roles.addAll(roles(userInfo.getOtherClaims().get(key), keys));
        }
        return roles;
    }

    public Collection<String> roles(Map<?, ?> map, String[] keys) {
        Set<String> roles = new HashSet<>();
        for (Object value : map.values()) {
            roles.addAll(roles(value, keys));
        }
        return roles;
    }

    public Collection<String> roles(List<?> list) {
        Set<String> roles = new HashSet<>();
        for (Object item : list) {
            roles.add(item.toString());
        }
        return roles;
    }
}
