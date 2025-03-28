package com.ecsolutions.cadastros.service;

import com.ecsolutions.cadastros.model.attributes.SecurityAttributes;
import com.ecsolutions.cadastros.model.dtos.keycloak.*;
import com.ecsolutions.cadastros.openfeign.clients.KeycloakAuthClient;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KeycloakService {

    private final UsersResource usersResource;

    private final KeycloakAuthClient keycloakAuthClient;

    private final RealmResource realmResource;


    public Optional<UserRepresentation> findById(String id) {
        try {
            var representation = this.usersResource.get(id).toRepresentation();
            return Optional.ofNullable(representation);
        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                return Optional.empty();
            }
            throw ex;
        }
    }

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
                    throw new RuntimeException("Unable to register user in keycloak, contact administrator");
                }
                var keycloakId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");

                return this.usersResource.get(keycloakId).toRepresentation();
            }




        } else {
            throw new BadRequestException("E-mail already registered");
        }


    }

    public boolean validateCredential(TokenRequest requestEntity) {
        try {
            OAuthTokenResponse tokenResponse = this.keycloakAuthClient.token(requestEntity);

            this.keycloakAuthClient.revoke(RevokeTokenRequest.builder()
                    .clientId(requestEntity.getClientId())
                    .token(tokenResponse.getAccessToken())
                    .tokenTypeHint("access_token")
                    .build());

            this.keycloakAuthClient.revoke(RevokeTokenRequest.builder()
                    .clientId(requestEntity.getClientId())
                    .token(tokenResponse.getRefreshToken())
                    .tokenTypeHint("refresh_token")
                    .build());
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public void changePassword(String id, ChangePasswordKeycloakRequest requestEntity) {
        var optional = this.findById(id);

        if (optional.isEmpty()) {
            throw new NotFoundException("User not found");
        }

        var userResource = this.usersResource.get(id);

        var representation = userResource.toRepresentation();

        var keycloakCredentials = new CredentialRepresentation();
        keycloakCredentials.setTemporary(false);
        keycloakCredentials.setValue(requestEntity.getPassword());
        representation.setCredentials(Collections.singletonList(keycloakCredentials));


        userResource.update(representation);

        this.invalidateSessions(id);
    }

    protected void invalidateSessions(String id) {
        var sessions = this.usersResource.get(id).getUserSessions();

        for (var session : sessions) {
            realmResource.deleteSession(session.getId());
        }
    }

}
