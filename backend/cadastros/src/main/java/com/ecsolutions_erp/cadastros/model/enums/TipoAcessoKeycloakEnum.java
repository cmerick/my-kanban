package com.ecsolutions_erp.cadastros.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum TipoAcessoKeycloakEnum {
    USUARIO_SISTEMA("ST"),
    CLIENTE_SISTEMA("CL"),
    ANONYMOUS("AN"),
    ;

    private final String value;

    public static final String APPLICATION_TYPE_KEY = "application.type";

    public static final String APPLICATION_RELACIONADO_ID_KEY = "application.relacionadoId";

    public static final String APPLICATION_CNPJ_KEY = "application.cnpj";

    public static final String TYPE_KEY = "type";

    public static final String RELACIONADO_ID_KEY = "relacionadoId";

    public static final String CNPJ_KEY = "cnpj";

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static TipoAcessoKeycloakEnum fromValue(String value) {
        return Arrays.stream(TipoAcessoKeycloakEnum.values())
                .filter(e -> e.value.equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid TipoAcessoKeycloakEnum: " + value));
    }

}
