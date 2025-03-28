package com.ecsolutions.cadastros.service;

import com.ecsolutions.cadastros.model.enums.KeycloakDefaultRealmRoleEnum;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RolesResource rolesResource;

    public Collection<RoleRepresentation> findAllSistemasRoles() {
        var roles = findAllRealmRoles();

        return roles.stream().toList();
    }

    public Collection<RoleRepresentation> findAllRealmRoles() {
        var roles = this.rolesResource.list(false);
        return roles.stream().filter(role -> !role.getClientRole()
                && Arrays
                .stream(KeycloakDefaultRealmRoleEnum.values())
                .noneMatch(defaultRole -> StringUtils.startsWith(role.getName(), defaultRole.getValue()))
        ).toList();
    }

}
