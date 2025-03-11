package com.ecsolutions.cadastros.openfeign.errordecoder;

import com.ecsolutions.cadastros.util.exceptions.ServerExceptionResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class MsIntegrationErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper;

    private final IntegrationErrorDecoder decoder;

    @Override
    public Exception decode(String methodKey, Response response) {
        if (null != response.body()) {
            try (var body = response.body().asInputStream()) {
                var responseBody = objectMapper.readValue(body, ServerExceptionResponse.class);
                return this.decoder.decode(response.status(), responseBody);
            } catch (IOException ex) {
                return new RuntimeException();
            }
        }

        return this.decoder.decode(response.status(), null);
    }
}
