package com.ecsolutions.cadastros.model.mappers;

import com.ecsolutions.cadastros.model.dtos.client.ClientResponseDto;
import com.ecsolutions.cadastros.persistence.entities.Client;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ClientMapper {

    private final ModelMapper modelMapper;

    public ClientResponseDto map(Client entityToMap) {
        return modelMapper.map(entityToMap, ClientResponseDto.class);
    }

    public List<ClientResponseDto> map(Collection<Client> entityToMap) {
        if(entityToMap == null) {
            return List.of();
        }

        return entityToMap.stream().map(this::map).toList();
    }
}
