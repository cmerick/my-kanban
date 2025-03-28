package com.ecsolutions.cadastros.model.dtos.project;

import com.ecsolutions.cadastros.model.enums.SimpleStatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResponseDto {

        private UUID id;
        private UUID creatorId;
        private UUID clientId;
        private String title;
        private String description;
        private SimpleStatusEnum status;
        private OffsetDateTime createdAt;
        private OffsetDateTime updatedAt;
}
