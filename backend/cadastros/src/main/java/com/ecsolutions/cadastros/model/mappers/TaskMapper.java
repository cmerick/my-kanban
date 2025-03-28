package com.ecsolutions.cadastros.model.mappers;

import com.ecsolutions.cadastros.model.dtos.task.TaskResponseDto;
import com.ecsolutions.cadastros.persistence.entities.Task;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TaskMapper {

    private final ModelMapper modelMapper;

    public TaskResponseDto map(Task entityToMap) {
        return modelMapper.map(entityToMap, TaskResponseDto.class);
    }

    public List<TaskResponseDto> map(Collection<Task> entityToMap) {
        if(entityToMap == null) {
            return List.of();
        }

        return entityToMap.stream().map(this::map).toList();
    }
}
