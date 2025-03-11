package com.ecsolutions.cadastros.openfeign.errordecoder;

import com.ecsolutions.cadastros.util.exceptions.ServerExceptionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IntegrationErrorDecoderImpl implements IntegrationErrorDecoder {
    @Override
    public Exception decode(int status, ServerExceptionResponse responseBody) {
        if (null != responseBody) {
            return this.message(status, responseBody.message());
        }
        return this.message(status, null);
    }

    private Exception message(int status, String message) {
        return switch (status) {
            case 400 -> new jakarta.ws.rs.BadRequestException(message);
            case 401, 403 -> new AccessDeniedException(message);
            case 404 -> new jakarta.ws.rs.NotFoundException(message);
            default -> new RuntimeException(message);
        };
    }
}
