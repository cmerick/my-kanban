package com.ecsolutions.cadastros.openfeign.configuration;

import com.ecsolutions.cadastros.model.dtos.security.AuthenticatedUser;
import com.ecsolutions.cadastros.openfeign.errordecoder.IntegrationErrorDecoder;
import com.ecsolutions.cadastros.openfeign.errordecoder.MsIntegrationErrorDecoder;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;

@RequiredArgsConstructor
public class UserTokenFeignConfig {

    private final AuthenticatedUser authenticatedUser;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> requestTemplate.header("Authorization", String.format("Bearer %s", this.authenticatedUser.getAccessToken()));
    }

    @Bean
    public ErrorDecoder errorDecoder(final ObjectMapper objectMapper, final IntegrationErrorDecoder decoder) {
        return new MsIntegrationErrorDecoder(objectMapper, decoder);
    }
}
