package com.ecsolutions.cadastros.service;

import com.ecsolutions.cadastros.model.dtos.acesso.UserRegisterRequest;
import com.ecsolutions.cadastros.model.dtos.keycloak.UserKeycloakRequest;
import com.ecsolutions.cadastros.model.enums.SimpleStatusEnum;
import com.ecsolutions.cadastros.model.enums.TypeAccessKeycloakEnum;
import com.ecsolutions.cadastros.persistence.entities.User;
import com.ecsolutions.cadastros.persistence.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final MessageSource messageSource;
    private final PasswordConstraintService passwordConstraintService;
    private final KeycloakService keycloakService;

    public Optional<User> findByEmail(String email){
        return this.repository.findByEmail(email);
    }

    @Transactional(rollbackOn = Throwable.class)
    public User create(UserRegisterRequest requestEntity) {
        var emailOp = this.findByEmail(requestEntity.getEmail());

        if(emailOp.isPresent()) {
            throw new BadRequestException(messageSource.getMessage("user.email.exists", null, LocaleContextHolder.getLocale()));
        }

        var hasPassword = StringUtils.isNotBlank(requestEntity.getPassword());
        if(hasPassword) {
            passwordConstraintService.isValid(requestEntity.getPassword());
        }

        var keycloakUser = keycloakService.create(
                UserKeycloakRequest.builder()
                        .username(requestEntity.getUsername())
                        .email(requestEntity.getEmail())
                        .groupsIds(List.of(requestEntity.getGruposId()))
                        .type(TypeAccessKeycloakEnum.SYSTEM)
                        .build()
        );

        return this.repository.saveAndFlush(User.builder()
                        .email(requestEntity.getEmail())
                        .username(requestEntity.getUsername())
                        .status(SimpleStatusEnum.ACTIVE)
                        .keycloakId(keycloakUser.getId())
                .build());
    }

    public String randomPassword() {
        RandomStringGenerator randomPass = new RandomStringGenerator.Builder()
                .withinRange(new char[]{'0', '9'}, new char[]{'a', 'z'}, new char[]{ 'A', 'Z'})
                        .build();
        return randomPass.generate(8);
    }
}
