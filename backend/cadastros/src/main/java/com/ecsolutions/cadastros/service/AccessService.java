package com.ecsolutions.cadastros.service;

import com.ecsolutions.cadastros.model.attributes.SecurityAttributes;
import com.ecsolutions.cadastros.model.dtos.acesso.ChangePasswordRequest;
import com.ecsolutions.cadastros.model.dtos.acesso.UserMeResponse;
import com.ecsolutions.cadastros.model.dtos.keycloak.ChangePasswordKeycloakRequest;
import com.ecsolutions.cadastros.model.dtos.keycloak.TokenRequest;
import com.ecsolutions.cadastros.model.dtos.security.AuthenticatedUser;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.UserInfo;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccessService {

    private final AuthenticatedUser authenticatedUser;

    private final KeycloakService keycloakService;

    private final UserService userService;

    private final SecurityAttributes securityAttributes;

    private final PasswordConstraintService passwordConstraintService;

    public UserMeResponse me() {

                var info = this.authenticatedUser.getInfo();

                var usuario = this.userService.findByKeycloakId(info.getSub())
                        .orElseThrow(NotFoundException::new);

                return UserMeResponse.builder()
                        .id(usuario.getId())
                        .username(usuario.getUsername())
                        .keycloakId(usuario.getKeycloakId())
                        .email(usuario.getEmail())
                        .status(usuario.getStatus()).build();

    }

    public void changePassword(ChangePasswordRequest requestEntity) {

        UserInfo info = authenticatedUser.getInfo();
        if (!this.keycloakService.validateCredential(
                TokenRequest.builder()
                        .clientId(securityAttributes.getClientIdUsers())
                        .grantType("password")
                        .username(info.getEmail())
                        .password(requestEntity.getActualPassword())
                        .build())
        ) {
            throw new BadRequestException("Senha atual inválida");
        }

        this.passwordConstraintService.isValid(requestEntity.getNewPassword());

        this.keycloakService.changePassword(info.getSub(), ChangePasswordKeycloakRequest.builder()
                .password(requestEntity.getNewPassword())
                .build());
    }
}
