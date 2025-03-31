package com.ecsolutions.cadastros.controller;

import com.ecsolutions.cadastros.model.dtos.client.ClientRequestDto;
import com.ecsolutions.cadastros.model.dtos.client.ClientResponseDto;
import com.ecsolutions.cadastros.model.mappers.ClientMapper;
import com.ecsolutions.cadastros.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    private final ClientMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientResponseDto create(@RequestBody ClientRequestDto dto) {
        return this.clientService.create(dto);
    }

    @PutMapping("/{id}")
    public ClientResponseDto update(@PathVariable UUID id, @Valid @RequestBody ClientRequestDto dto) {
        return this.clientService.update(id, dto);
    }

    @GetMapping
    public List<ClientResponseDto> findAll() {
        return this.clientService.findAll();
    }

    @GetMapping("/{id}")
    public ClientResponseDto findById(@PathVariable UUID id) {
        return mapper.map(this.clientService.findById(id));
    }

    @DeleteMapping("/{id}/toggle-status")
    public void toggleStatus(@PathVariable UUID id) {
        this.clientService.toggleStatus(id);
    }
}

