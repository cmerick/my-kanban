package com.ecsolutions.cadastros.model.mappers;

import com.ecsolutions.cadastros.model.dtos.project.ProjectResponseDto;
import com.ecsolutions.cadastros.persistence.entities.Project;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProjectMapper {

    private final ModelMapper modelMapper;

    public ProjectResponseDto map(Project entityToMap) {
        return modelMapper.map(entityToMap, ProjectResponseDto.class);
    }

    public List<ProjectResponseDto> map(Collection<Project> entityToMap) {
        if(entityToMap == null) {
            return List.of();
        }

        return entityToMap.stream().map(this::map).toList();
    }
}
