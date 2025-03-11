package com.ecsolutions.cadastros.model.dtos.security;

import com.ecsolutions.cadastros.model.enums.TypeAccessKeycloakEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.UserInfo;

import java.util.UUID;

@RequiredArgsConstructor
@Builder
public class AuthenticatedUser {

    @Getter
    private final UserInfo info;

    @Getter
    private final String tokenType;

    @Getter
    private final String accessToken;

    @Getter
    private final String realm;

    @Getter
    private final String clientId;

    @Getter
    private final KeycloakApplicationAttributes applicationAttributes;

    @JsonIgnore
    public UUID getUserId() {
        if (null != this.info) {
            return UUID.fromString(this.info.getSub());
        }
        return null;
    }

    @JsonIgnore
    public TypeAccessKeycloakEnum getType() {
        if (null != this.applicationAttributes) {
            return this.applicationAttributes.getType();
        }
        return null;
    }

}
