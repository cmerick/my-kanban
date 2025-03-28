package com.ecsolutions.cadastros.service;

import com.ecsolutions.cadastros.model.dtos.taskstatus.TaskStatusRequestDto;
import com.ecsolutions.cadastros.model.dtos.taskstatus.TaskStatusResponseDto;
import com.ecsolutions.cadastros.model.enums.SimpleStatusEnum;
import com.ecsolutions.cadastros.model.mappers.TaskStatusMapper;
import com.ecsolutions.cadastros.persistence.entities.TaskStatus;
import com.ecsolutions.cadastros.persistence.repository.TaskStatusRepository;
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
public class TaskStatusService {

    private final TaskStatusRepository taskStatusRepository;

    private final TaskStatusMapper mapper;

    @Transactional(rollbackOn = Throwable.class)
    public TaskStatusResponseDto create(TaskStatusRequestDto dto) {
        var taskStatus = TaskStatus.builder()
                .name(dto.getName())
                .status(SimpleStatusEnum.ACTIVE)
                .updatedAt(OffsetDateTime.now())
                .build();

        return mapper.map(this.taskStatusRepository.save(taskStatus));
    }

    @Transactional(rollbackOn = Throwable.class)
    public TaskStatusResponseDto update(UUID id, TaskStatusRequestDto updatedTaskStatus) {
        var existingTaskStatus = this.findById(id);
        existingTaskStatus.setName(updatedTaskStatus.getName());
        existingTaskStatus.setUpdatedAt(OffsetDateTime.now());
        return mapper.map(this.taskStatusRepository.save(existingTaskStatus));
    }


    public List<TaskStatusResponseDto> findAll() {
        return mapper.map(this.taskStatusRepository.findAll());
    }


    public TaskStatus findById(UUID id) {
        return this.taskStatusRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("TaskStatus not found"));
    }

    public void toggleStatus(UUID id) {
        var taskStatus = this.findById(id);
        taskStatus.setStatus(taskStatus.getStatus() == SimpleStatusEnum.ACTIVE ? SimpleStatusEnum.INACTIVE : SimpleStatusEnum.ACTIVE);
        taskStatus.setUpdatedAt(OffsetDateTime.now());
        this.taskStatusRepository.save(taskStatus);
    }
}