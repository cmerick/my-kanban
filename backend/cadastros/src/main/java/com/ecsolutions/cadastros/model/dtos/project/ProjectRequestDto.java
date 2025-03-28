package com.ecsolutions.cadastros.model.dtos.project;

import com.ecsolutions.cadastros.model.enums.SimpleStatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectRequestDto {

        @NotNull
        private UUID creatorId;

        @NotNull
        private UUID clientId;

        @NotBlank
        private String title;

        @NotBlank
        private String description;

        @NotNull
        private SimpleStatusEnum status;
}
