package com.ecsolutions.cadastros.model.dtos.keycloak;

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

    private boolean master;

}
