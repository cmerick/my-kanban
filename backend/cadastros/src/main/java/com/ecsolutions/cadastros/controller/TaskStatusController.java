package com.ecsolutions.cadastros.controller;


import com.ecsolutions.cadastros.model.dtos.taskstatus.TaskStatusRequestDto;
import com.ecsolutions.cadastros.model.dtos.taskstatus.TaskStatusResponseDto;
import com.ecsolutions.cadastros.model.mappers.TaskStatusMapper;
import com.ecsolutions.cadastros.service.TaskStatusService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/parameters/task-status")
@RequiredArgsConstructor
public class TaskStatusController {

    private final TaskStatusService clientService;

    private final TaskStatusMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskStatusResponseDto create(@RequestBody TaskStatusRequestDto dto) {
        return this.clientService.create(dto);
    }

    @PutMapping("/{id}")
    public TaskStatusResponseDto update(@PathVariable UUID id, @Valid @RequestBody TaskStatusRequestDto dto) {
        return this.clientService.update(id, dto);
    }

    @GetMapping
    public List<TaskStatusResponseDto> findAll() {
        return this.clientService.findAll();
    }

    @GetMapping("/{id}")
    public TaskStatusResponseDto findById(@PathVariable UUID id) {
        return mapper.map(this.clientService.findById(id));
    }

    @DeleteMapping("/{id}/toggle-status")
    public void toggleStatus(@PathVariable UUID id) {
        this.clientService.toggleStatus(id);
    }
}

