package com.ecsolutions.cadastros.controller;

import com.ecsolutions.cadastros.model.dtos.task.TaskRequestDto;
import com.ecsolutions.cadastros.model.dtos.task.TaskResponseDto;
import com.ecsolutions.cadastros.model.mappers.TaskMapper;
import com.ecsolutions.cadastros.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    private final TaskMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponseDto create(@Valid @RequestBody TaskRequestDto dto) {
        return this.taskService.create(dto);
    }

    @PutMapping("/{id}")
    public TaskResponseDto update(@PathVariable Long id, @Valid @RequestBody TaskRequestDto dto) {
        return this.taskService.update(id, dto);
    }

    @GetMapping
    public List<TaskResponseDto> findAll() {
        return this.taskService.findAll();
    }

    @GetMapping("/{id}")
    public TaskResponseDto findById(@PathVariable Long id) {
        return mapper.map(this.taskService.findById(id));
    }
}

