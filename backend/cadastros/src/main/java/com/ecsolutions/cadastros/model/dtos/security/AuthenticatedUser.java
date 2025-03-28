package com.ecsolutions.cadastros.model.dtos.security;

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


    @JsonIgnore
    public UUID getUserId() {
        if (null != this.info) {
            return UUID.fromString(this.info.getSub());
        }
        return null;
    }


}
