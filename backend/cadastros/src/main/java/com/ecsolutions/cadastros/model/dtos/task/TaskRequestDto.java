package com.ecsolutions.cadastros.model.dtos.task;

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
public class TaskRequestDto {

        private UUID projectId;

        private UUID parentTaskId;

        private UUID creatorId;

        private UUID assignedTo;

        private String title;

        private String description;

        private OffsetDateTime startDate;

        private OffsetDateTime dueDate;

        private Integer progress;

        private UUID status;
}
