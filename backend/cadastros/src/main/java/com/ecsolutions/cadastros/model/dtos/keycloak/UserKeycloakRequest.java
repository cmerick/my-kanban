package com.ecsolutions.cadastros.model.dtos.keycloak;

import com.ecsolutions.cadastros.model.enums.TypeAccessKeycloakEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserKeycloakRequest {

    private String username;

    private String email;

    private String password;

    private Collection<String> groupsIds;

    private TypeAccessKeycloakEnum type;

    private boolean master;

    public Map<String, List<String>> getAttributes() {
        var attributes = new HashMap<String, List<String>>();
        attributes.put(TypeAccessKeycloakEnum.APPLICATION_TYPE_KEY, List.of(type.getValue()));

        return attributes;
    }

}
