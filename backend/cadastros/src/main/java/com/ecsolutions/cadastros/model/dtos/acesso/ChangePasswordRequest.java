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
public class ChangePasswordRequest {

    private String actualPassword;

    private String newPassword;


}
