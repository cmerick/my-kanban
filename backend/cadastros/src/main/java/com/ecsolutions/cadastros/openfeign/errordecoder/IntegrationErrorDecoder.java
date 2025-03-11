package com.ecsolutions.cadastros.openfeign.errordecoder;


import com.ecsolutions.cadastros.util.exceptions.ServerExceptionResponse;

public interface IntegrationErrorDecoder {

    Exception decode(int status, ServerExceptionResponse responseBody);

}
