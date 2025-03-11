package com.ecsolutions.cadastros.persistence.entities;


import com.ecsolutions.cadastros.model.enums.SimpleStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tb_tasks")
public class Task implements Serializable {

    @Serial
    private static final long serialVersionUID = 6436968871178057858L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tasks_seq")
    @SequenceGenerator(name = "tasks_seq", sequenceName = "tasks_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "project_id", nullable = false)
    private UUID projectId;

    @Column(name = "parent_task_id", nullable = false)
    private UUID parentTaskId;

    @Column(name = "creator_id", nullable = false)
    private UUID creatorId;

    @Column(name = "assigned_to", nullable = false)
    private UUID assignedTo;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "start_date")
    private OffsetDateTime startDate;

    @Column(name = "due_date")
    private OffsetDateTime dueDate;

    @Column(name = "created_at", columnDefinition = "TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP")
    private OffsetDateTime updatedAt;

    @Column(name= "status", nullable = false)
    private SimpleStatusEnum status;

}
