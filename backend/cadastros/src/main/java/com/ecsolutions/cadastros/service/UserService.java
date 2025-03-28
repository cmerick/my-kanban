package com.ecsolutions.cadastros.service;

import com.ecsolutions.cadastros.model.dtos.acesso.UserRegisterRequest;
import com.ecsolutions.cadastros.model.dtos.acesso.UserResponse;
import com.ecsolutions.cadastros.model.dtos.acesso.UserSearchRequest;
import com.ecsolutions.cadastros.model.dtos.keycloak.UserKeycloakRequest;
import com.ecsolutions.cadastros.model.enums.SimpleStatusEnum;
import com.ecsolutions.cadastros.model.mappers.UserMapper;
import com.ecsolutions.cadastros.persistence.entities.User;
import com.ecsolutions.cadastros.persistence.repository.UserRepository;
import com.ecsolutions.cadastros.persistence.specifications.UserSpecification;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordConstraintService passwordConstraintService;
    private final KeycloakService keycloakService;
    private final UserMapper mapper;

    public Collection<User> findAll(UserSearchRequest requestEntity) {
        return repository.findAll(
                Specification
                        .where(UserSpecification.likeNome(requestEntity.getUsername()))
                        .and(UserSpecification.likeEmail(requestEntity.getEmail()))
                        .and(UserSpecification.status(requestEntity.getStatus()))
        );
    }

    public Collection<UserResponse> getAll(UserSearchRequest requestEntity) {
        return this.mapper.map(this.findAll(requestEntity));
    }

    public Optional<User> findByEmail(String email){
        return this.repository.findByEmail(email);
    }

    public Optional<User> findById(UUID id) {
        return this.repository.findById(id);
    }

    public UserResponse getById(UUID id) {
        var entity = this.findById(id).orElseThrow(NotFoundException::new);
        return this.mapper.map(entity);
    }

    @Transactional(rollbackOn = Throwable.class)
    public User create(UserRegisterRequest requestEntity) {
        var emailOp = this.findByEmail(requestEntity.getEmail());

        if(emailOp.isPresent()) {
            throw new BadRequestException("User E-mail already registered");
        }

        var hasPassword = StringUtils.isNotBlank(requestEntity.getPassword());
        if(hasPassword) {
            passwordConstraintService.isValid(requestEntity.getPassword());
        }

        var keycloakUser = keycloakService.create(
                UserKeycloakRequest.builder()
                        .username(requestEntity.getUsername())
                        .email(requestEntity.getEmail())
                        .groupsIds(List.of(requestEntity.getGroupsId()))
                        .build()
        );

        return this.repository.saveAndFlush(User.builder()
                        .email(requestEntity.getEmail())
                        .username(requestEntity.getUsername())
                        .status(SimpleStatusEnum.ACTIVE)
                        .keycloakId(keycloakUser.getId())
                .build());
    }

    public Optional<User> findByKeycloakId(String keycloakId) {
        return this.repository.findByKeycloakId(keycloakId);
    }

}
