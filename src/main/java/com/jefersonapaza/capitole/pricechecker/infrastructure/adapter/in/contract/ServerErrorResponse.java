package com.jefersonapaza.capitole.pricechecker.infrastructure.adapter.in.contract;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@Builder
@Schema(description = "Respuesta para errores de servidor")
public class ServerErrorResponse {
    private LocalDateTime timestamp;
    private int code;
    private String message;
}