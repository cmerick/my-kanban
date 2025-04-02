package com.ecsolutions.cadastros.controller;

import com.ecsolutions.cadastros.model.dtos.project.ProjectRequestDto;
import com.ecsolutions.cadastros.model.dtos.project.ProjectResponseDto;
import com.ecsolutions.cadastros.model.mappers.ProjectMapper;
import com.ecsolutions.cadastros.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    private final ProjectMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectResponseDto create(@RequestBody ProjectRequestDto dto) {
        return this.projectService.create(dto);
    }

    @PutMapping("/{id}")
    public ProjectResponseDto update(@PathVariable UUID id, @Valid @RequestBody ProjectRequestDto dto) {
        return this.projectService.update(id, dto);
    }

    @GetMapping
    public List<ProjectResponseDto> findAll() {
        return this.projectService.findAll();
    }

    @GetMapping("/{id}")
    public ProjectResponseDto findById(@PathVariable UUID id) {
        return mapper.map(this.projectService.findById(id));
    }

    @GetMapping("client/{id}")
    public List<ProjectResponseDto> findByClientId(@PathVariable UUID id) {
        return this.projectService.findByClientId(id);
    }


    @DeleteMapping("/{id}/toggle-status")
    public void toggleStatus(@PathVariable UUID id) {
        this.projectService.toggleStatus(id);
    }
}

