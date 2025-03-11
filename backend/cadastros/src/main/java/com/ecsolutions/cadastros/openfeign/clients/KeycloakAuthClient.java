package com.ecsolutions.cadastros.openfeign.clients;

import com.ecsolutions.cadastros.model.dtos.keycloak.OAuthTokenResponse;
import com.ecsolutions.cadastros.model.dtos.keycloak.RevokeTokenRequest;
import com.ecsolutions.cadastros.model.dtos.keycloak.TokenRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "keycloak-auth-client",
        url = "${api.oauth.url}/realms/${api.oauth.realm}/protocol/openid-connect")
public interface KeycloakAuthClient {

    @PostMapping(value = "token", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    OAuthTokenResponse token(@RequestBody TokenRequest requestEntity);

    @PostMapping(value = "revoke", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    ResponseEntity<Void> revoke(@RequestBody RevokeTokenRequest requestEntity);

}
