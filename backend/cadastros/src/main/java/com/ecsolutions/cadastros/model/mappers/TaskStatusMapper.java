package com.ecsolutions.cadastros.model.mappers;

import com.ecsolutions.cadastros.model.dtos.taskstatus.TaskStatusResponseDto;
import com.ecsolutions.cadastros.persistence.entities.TaskStatus;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TaskStatusMapper {

    private final ModelMapper modelMapper;

    public TaskStatusResponseDto map(TaskStatus entityToMap) {
        return modelMapper.map(entityToMap, TaskStatusResponseDto.class);
    }

    public List<TaskStatusResponseDto> map(Collection<TaskStatus> entityToMap) {
        if(entityToMap == null) {
            return List.of();
        }

        return entityToMap.stream().map(this::map).toList();
    }
}
