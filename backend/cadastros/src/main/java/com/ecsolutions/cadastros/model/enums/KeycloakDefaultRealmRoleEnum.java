package com.ecsolutions.cadastros.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum KeycloakDefaultRealmRoleEnum {
    UMA_AUTHORIZATION("uma_authorization"),
    OFFLINE_ACCESS("offline_access"),
    DEFAULT_ROLES("default-roles-"),
    ;
    private final String value;

}
