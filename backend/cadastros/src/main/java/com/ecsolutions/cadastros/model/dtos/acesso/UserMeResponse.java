package com.ecsolutions.cadastros.model.dtos.acesso;

import com.ecsolutions.cadastros.model.enums.SimpleStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserMeResponse {

    private UUID id;

    private String keycloakId;

    private String email;

    private String username;

    private SimpleStatusEnum status;



}
