package com.ecsolutions.cadastros.service;

import com.ecsolutions.cadastros.model.dtos.client.ClientResponseDto;
import com.ecsolutions.cadastros.model.dtos.project.ProjectRequestDto;
import com.ecsolutions.cadastros.model.dtos.project.ProjectResponseDto;
import com.ecsolutions.cadastros.model.enums.SimpleStatusEnum;
import com.ecsolutions.cadastros.model.mappers.ProjectMapper;
import com.ecsolutions.cadastros.persistence.entities.Project;
import com.ecsolutions.cadastros.persistence.repository.ProjectRepository;
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
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper mapper;

    @Transactional(rollbackOn = Throwable.class)
    public ProjectResponseDto create(ProjectRequestDto dto) {
        var entity = projectRepository.save(Project.builder()
                        .creatorId(dto.getCreatorId())
                        .updatedAt(OffsetDateTime.now())
                        .clientId(dto.getClientId())
                        .description(dto.getDescription())
                        .title(dto.getTitle())
                        .status(SimpleStatusEnum.ACTIVE)
                .build());
        return mapper.map(entity);
    }

    @Transactional(rollbackOn = Throwable.class)
    public ProjectResponseDto update(UUID id, ProjectRequestDto updateProject) {
        var existingProject = this.findById(id);
        existingProject.setUpdatedAt(OffsetDateTime.now());
        existingProject.setTitle(updateProject.getTitle());
        existingProject.setDescription(updateProject.getDescription());

        return mapper.map(this.projectRepository.save(existingProject));
    }

    public List<ProjectResponseDto> findAll() {
        return mapper.map(this.projectRepository.findAll());
    }

    public Project findById(UUID id) {
        return this.projectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Project not found"));
    }

    public void toggleStatus(UUID id) {
        var client = this.findById(id);
        client.setStatus(client.getStatus() == SimpleStatusEnum.ACTIVE ? SimpleStatusEnum.INACTIVE : SimpleStatusEnum.ACTIVE);
        client.setUpdatedAt(OffsetDateTime.now());
        this.projectRepository.save(client);
    }
}
