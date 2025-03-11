package com.ecsolutions.cadastros.openfeign.clients;


import com.ecsolutions.cadastros.model.dtos.keycloak.GroupKeycloakResponse;
import com.ecsolutions.cadastros.openfeign.configuration.OAuth2InternalFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;

@FeignClient(
        name = "keycloak-groups-client",
        url = "${api.oauth.url}/admin/realms/${api.oauth.realm}/groups",
        configuration = OAuth2InternalFeignConfig.class
)
public interface KeycloakGroupsClient {

    @GetMapping
    Collection<GroupKeycloakResponse> groups(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "q", required = false) String query,
            @RequestParam("briefRepresentation") boolean briefRepresentation
    );

}
