package com.ecsolutions.cadastros.util.exceptions;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

public record ServerExceptionResponse(@JsonProperty("message") String message,
                                      @JsonProperty("timestamp") OffsetDateTime timestamp) {
}
