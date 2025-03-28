package com.ecsolutions.cadastros.model.dtos.taskstatus;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskStatusRequestDto {
        @NotBlank
        private String name;
}
