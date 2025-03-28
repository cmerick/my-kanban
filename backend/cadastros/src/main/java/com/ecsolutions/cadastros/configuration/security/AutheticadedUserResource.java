package com.ecsolutions.cadastros.configuration.security;

import com.ecsolutions.cadastros.model.dtos.security.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AutheticadedUserResource {

    private final AuthenticatedUser authenticatedUser;

}
