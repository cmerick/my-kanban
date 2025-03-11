package com.ecsolutions.cadastros.model.dtos.keycloak;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupKeycloakResponse {

    public String id;

    public String name;

    public String path;

    public Integer subGroupCount;

    public List<GroupKeycloakResponse> subGroups;

    public GroupKeycloakAttributes attributes;

    public List<String> realmRoles;

}
