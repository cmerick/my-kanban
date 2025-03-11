package com.ecsolutions.cadastros.service;

import com.ecsolutions.cadastros.model.attributes.SecurityAttributes;
import com.ecsolutions.cadastros.model.dtos.keycloak.UserKeycloakRequest;
import com.ecsolutions.cadastros.model.enums.TypeAccessKeycloakEnum;
import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class KeycloakService {

    private final SecurityAttributes securityAttributes;

    private final UsersResource usersResource;

    private final KeycloakGroupsService keycloakGroupsService;

    private final MessageSource messageSource;

    private static CredentialRepresentation createPasswordCredentials(String password) {
        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setTemporary(false);
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(password);
        return passwordCred;
    }

    public UserRepresentation create(UserKeycloakRequest requestEntity) {
        var opKeycloak = this.usersResource.search(requestEntity.getEmail());

        if(opKeycloak.isEmpty()) {
            UserRepresentation user = new UserRepresentation();
            user.setUsername(requestEntity.getUsername());
            user.setEmail(requestEntity.getEmail());
            user.setEnabled(true);
            user.setEmailVerified(true);
            user.setCredentials(Collections.singletonList(createPasswordCredentials(requestEntity.getPassword())));


            try (var response = this.usersResource.create(user)) {
                if (response.getStatus() >= 400) {
                    throw new RuntimeException(messageSource.getMessage("user.keycloak.creation.error", null, LocaleContextHolder.getLocale()));
                }
                var keycloakId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");

                this.keycloakGroupsService.addGroupsById(keycloakId, requestEntity.getGroupsIds());

                updateDefaultRealmRoles(keycloakId, requestEntity.getType(), requestEntity.isMaster());

                return this.usersResource.get(keycloakId).toRepresentation();
            }




        } else {
            throw new BadRequestException(messageSource.getMessage("user.email.exists", null, LocaleContextHolder.getLocale()));
        }


    }

    protected void updateDefaultRealmRoles(String id, TypeAccessKeycloakEnum tipoAcesso, boolean master) {
        if (!Objects.equals(TypeAccessKeycloakEnum.SYSTEM, tipoAcesso)) {
            var userResource = this.usersResource.get(id);

            var rolesToRemove = userResource.roles().realmLevel().listAll().stream()
                    .filter(r -> !StringUtils.containsIgnoreCase(
                            r.getName(),
                            "default-roles-%s".formatted(securityAttributes.getRealm())
                    ))
                    .toList();
            userResource.roles().realmLevel().remove(rolesToRemove);

            if (master) {
                List<RoleRepresentation> rolesRepresentations = switch (tipoAcesso) {
                    default -> Collections.emptyList();
                };

                userResource.roles().realmLevel().add(rolesRepresentations);
            }
        }
    }
}
