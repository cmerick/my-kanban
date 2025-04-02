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

        private UUID creatorId;

        private UUID clientId;

        private String title;

        private String description;
}
