package com.ecsolutions.cadastros.model.mappers;

import com.ecsolutions.cadastros.model.dtos.acesso.UserResponse;
import com.ecsolutions.cadastros.persistence.entities.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ModelMapper modelMapper;

    public UserResponse map(User entityToMap) {
        return modelMapper.map(entityToMap, UserResponse.class);
    }

    public List<UserResponse> map(Collection<User> entityToMap) {
        if(entityToMap == null) {
            return List.of();
        }

        return entityToMap.stream().map(this::map).toList();
    }
}
