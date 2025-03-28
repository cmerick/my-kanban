package com.ecsolutions.cadastros.model.dtos.client;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientRequestDto{
        @NotBlank private String name;
}
