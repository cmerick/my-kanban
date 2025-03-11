package com.ecsolutions.cadastros.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum SimpleStatusEnum {
    ACTIVE("A"),
    INACTIVE("I"),
    ;

    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static SimpleStatusEnum fromValue(String value) {
        return Arrays.stream(SimpleStatusEnum.values())
                .filter(e -> e.value.equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid TipoAcessoKeycloakEnum: " + value));
    }

}
