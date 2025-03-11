package com.ecsolutions.cadastros.model.dtos.acesso;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSearchRequest {

    private String username;
    private String email;

}
