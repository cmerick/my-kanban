package com.ecsolutions.cadastros.openfeign.configuration;

import com.ecsolutions.cadastros.model.attributes.SecurityAttributes;
import com.ecsolutions.cadastros.model.dtos.keycloak.OAuthTokenResponse;
import com.ecsolutions.cadastros.openfeign.errordecoder.IntegrationErrorDecoder;
import com.ecsolutions.cadastros.openfeign.errordecoder.MsIntegrationErrorDecoder;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
public class OAuth2InternalFeignConfig {

    private final SecurityAttributes attributes;

    protected String getAccessToken() {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", attributes.getClientId());
        map.add("client_secret", attributes.getClientSecret());
        map.add("grant_type", attributes.getGranType());

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map, headers);
        OAuthTokenResponse responseEntity = restTemplate.postForObject(
                "%s/realms/%s/protocol/openid-connect/token".formatted(attributes.getUrl(), attributes.getRealm()),
                httpEntity,
                OAuthTokenResponse.class);

        return responseEntity.getAccessToken();
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> requestTemplate.header("Authorization", "Bearer %s".formatted(this.getAccessToken()));
    }

    @Bean
    public ErrorDecoder errorDecoder(final ObjectMapper objectMapper, final IntegrationErrorDecoder decoder) {
        return new MsIntegrationErrorDecoder(objectMapper, decoder);
    }

}
