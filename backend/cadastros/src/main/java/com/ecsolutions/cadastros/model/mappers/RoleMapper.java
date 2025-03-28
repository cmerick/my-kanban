package com.ecsolutions.cadastros.model.mappers;

import com.ecsolutions.cadastros.model.dtos.keycloak.RoleResponse;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class RoleMapper {

    public RoleResponse map(RoleRepresentation entityToMap) {
        return RoleResponse.builder()
                .id(entityToMap.getId())
                .name(entityToMap.getName())
                .description(entityToMap.getDescription())
                .build();
    }

    public List<RoleResponse> map(Collection<RoleRepresentation> entityToMap) {
        if (entityToMap == null) {
            return List.of();
        }
        return entityToMap.stream().map(this::map).toList();
    }

}
