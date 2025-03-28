package com.ecsolutions.cadastros.service;

import com.ecsolutions.cadastros.model.dtos.task.TaskRequestDto;
import com.ecsolutions.cadastros.model.dtos.task.TaskResponseDto;
import com.ecsolutions.cadastros.model.enums.SimpleStatusEnum;
import com.ecsolutions.cadastros.model.mappers.TaskMapper;
import com.ecsolutions.cadastros.persistence.entities.Task;
import com.ecsolutions.cadastros.persistence.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper mapper;

    @Transactional(rollbackOn = Throwable.class)
    public TaskResponseDto create(TaskRequestDto dto) {
        var entity = taskRepository.save(Task.builder()
                        .projectId(dto.getProjectId())
                        .parentTaskId(dto.getParentTaskId())
                        .assignedTo(dto.getAssignedTo())
                        .startDate(dto.getStartDate())
                        .dueDate(dto.getDueDate())
                        .creatorId(dto.getCreatorId())
                        .updatedAt(OffsetDateTime.now())
                        .description(dto.getDescription())
                        .progress(dto.getProgress())
                        .title(dto.getTitle())
                        .status(dto.getStatus())
                .build());
        return mapper.map(entity);
    }

    @Transactional(rollbackOn = Throwable.class)
    public TaskResponseDto update(Long id, TaskRequestDto updateTask) {
        var existingTask = this.findById(id);
        existingTask.setUpdatedAt(OffsetDateTime.now());
        existingTask.setTitle(updateTask.getTitle());
        existingTask.setDescription(updateTask.getDescription());
        existingTask.setStartDate(updateTask.getStartDate());
        existingTask.setDueDate(updateTask.getDueDate());
        existingTask.setProgress(updateTask.getProgress());
        existingTask.setAssignedTo(updateTask.getAssignedTo());
        existingTask.setParentTaskId(updateTask.getParentTaskId());
        existingTask.setProjectId(updateTask.getProjectId());


        return mapper.map(this.taskRepository.save(existingTask));
    }

    public List<TaskResponseDto> findAll() {
        return mapper.map(this.taskRepository.findAll());
    }

    public Task findById(Long id) {
        return this.taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));
    }
}
