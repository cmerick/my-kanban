package com.ecsolutions.cadastros.model.dtos.keycloak;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupKeycloakAttributes implements Serializable {

    @Serial
    private static final long serialVersionUID = 6166055110337278837L;

    private List<String> type;

    private Collection<String> relacionadoId;

}
