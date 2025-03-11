package com.ecsolutions.cadastros.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum TypeAccessKeycloakEnum {
    SYSTEM("ST"),
    CLIENT("CL"),
    ANONYMOUS("AN"),
    ;

    private final String value;

    public static final String APPLICATION_TYPE_KEY = "application.type";

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static TypeAccessKeycloakEnum fromValue(String value) {
        return Arrays.stream(TypeAccessKeycloakEnum.values())
                .filter(e -> e.value.equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid TypeAccessKeycloakEnum: " + value));
    }

}
