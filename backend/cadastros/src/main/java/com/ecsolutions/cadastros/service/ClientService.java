package com.ecsolutions.cadastros.service;

import com.ecsolutions.cadastros.model.dtos.client.ClientRequestDto;
import com.ecsolutions.cadastros.model.dtos.client.ClientResponseDto;
import com.ecsolutions.cadastros.model.enums.SimpleStatusEnum;
import com.ecsolutions.cadastros.model.mappers.ClientMapper;
import com.ecsolutions.cadastros.persistence.entities.Client;
import com.ecsolutions.cadastros.persistence.repository.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientService {

    private final ClientRepository clientRepository;

    private final ClientMapper mapper;

    @Transactional(rollbackOn = Throwable.class)
    public ClientResponseDto create(ClientRequestDto dto) {
        var client = Client.builder()
                .name(dto.getName())
                .status(SimpleStatusEnum.ACTIVE)
                .updatedAt(OffsetDateTime.now())
                .build();

        return mapper.map(this.clientRepository.save(client));
    }

    @Transactional(rollbackOn = Throwable.class)
    public ClientResponseDto update(UUID id, ClientRequestDto updatedClient) {
        var existingClient = this.findById(id);
        existingClient.setName(updatedClient.getName());
        existingClient.setUpdatedAt(OffsetDateTime.now());
        return mapper.map(this.clientRepository.save(existingClient));
    }


    public List<ClientResponseDto> findAll() {
        return mapper.map(this.clientRepository.findAll())
                .stream()
                .sorted(
                        Comparator.comparingInt((ClientResponseDto c) -> c.getStatus().equals(SimpleStatusEnum.ACTIVE) ? 0 : 1) // Ativos vêm primeiro
                                .thenComparing(ClientResponseDto::getCreatedAt, Comparator.reverseOrder())) // Mais recentes primeiro
                .collect(Collectors.toList());
    }



    public Client findById(UUID id) {
        return this.clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));
    }

    public void toggleStatus(UUID id) {
        var client = this.findById(id);
        client.setStatus(client.getStatus() == SimpleStatusEnum.ACTIVE ? SimpleStatusEnum.INACTIVE : SimpleStatusEnum.ACTIVE);
        client.setUpdatedAt(OffsetDateTime.now());
        this.clientRepository.save(client);
    }
}