package com.ecsolutions.cadastros.configuration.security;

import com.ecsolutions.cadastros.model.attributes.SecurityAttributes;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfiguration {

    @Bean("keycloakAdminClient")
    public Keycloak keycloakAdminClient(SecurityAttributes securityAttributes) {
        return KeycloakBuilder.builder()
                .serverUrl(securityAttributes.getUrl())
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .clientId(securityAttributes.getClientId())
                .clientSecret(securityAttributes.getClientSecret())
                .realm(securityAttributes.getRealm())
                .scope("email profile openid")
                .build();
    }
    @Bean("keycloakAdminRealmResource")
    RealmResource keycloakAdminRealmResource(
            @Qualifier("keycloakAdminClient") final Keycloak keycloakAdminClient,
            final SecurityAttributes securityAttributes) {
        return keycloakAdminClient.realm(securityAttributes.getRealm());
    }

    @Bean("keycloakAdminUsersResource")
    UsersResource keycloakAdminUsersResource(@Qualifier("keycloakAdminRealmResource") final RealmResource realmResource) {
        return realmResource.users();
    }

    @Bean("keycloakAdminGroupResource")
    GroupsResource keycloakAdminGroupResource(@Qualifier("keycloakAdminRealmResource") final RealmResource realmResource) {
        return realmResource.groups();
    }

    @Bean("keycloakAdminRolesResource")
    RolesResource keycloakAdminRolesResource(@Qualifier("keycloakAdminRealmResource") final RealmResource realmResource) {
        return realmResource.roles();
    }

    @Bean("keycloakAdminClientsResource")
    ClientsResource keycloakAdminClientsResource(@Qualifier("keycloakAdminRealmResource") final RealmResource realmResource) {
        return realmResource.clients();
    }
}
