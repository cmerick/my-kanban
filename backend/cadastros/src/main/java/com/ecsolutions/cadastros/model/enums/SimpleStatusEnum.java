package com.ecsolutions.cadastros.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum SimpleStatusEnum {
    ACTIVE("A", "Active"),
    INACTIVE("I", "Inactive");

    private final String value;
    private final String descricao;

    private static final Map<String, SimpleStatusEnum> VALUE_MAP = Arrays.stream(values())
            .collect(Collectors.toMap(SimpleStatusEnum::getValue, Function.identity()));

    @JsonCreator
    public static SimpleStatusEnum fromValue(String value) {
        SimpleStatusEnum status = VALUE_MAP.get(value);
        if (status == null) {
            throw new IllegalArgumentException("Invalid value for SimpleStatusEnum: " + value);
        }
        return status;
    }


    @JsonValue
    public String getValue() {
        return value;
    }

}
