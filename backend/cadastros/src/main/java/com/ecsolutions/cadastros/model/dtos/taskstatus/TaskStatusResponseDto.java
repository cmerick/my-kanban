package com.ecsolutions.cadastros.model.dtos.taskstatus;

import com.ecsolutions.cadastros.model.enums.SimpleStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskStatusResponseDto {
    private UUID id;
    private String name;
    private SimpleStatusEnum status;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

}
