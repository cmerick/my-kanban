package com.ecsolutions.cadastros.model.dtos.security;

import com.ecsolutions.cadastros.model.enums.TypeAccessKeycloakEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KeycloakApplicationAttributes implements Serializable {

    @Serial
    private static final long serialVersionUID = 2275591786689092126L;

    @JsonProperty("type")
    private TypeAccessKeycloakEnum type;

    @JsonProperty("cnpj")
    private String cnpj;

    @JsonProperty("relacionadoId")
    private String relacionadoId;

}
