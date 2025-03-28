package com.ecsolutions.cadastros.model.dtos.acesso;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegisterRequest {

    private String username;
    private String email;
    private String password;
    private String groupsId;

}
