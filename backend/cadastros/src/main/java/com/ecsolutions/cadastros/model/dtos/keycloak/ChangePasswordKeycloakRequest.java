package com.ecsolutions.cadastros.model.dtos.keycloak;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangePasswordKeycloakRequest {

    private String password;


}
