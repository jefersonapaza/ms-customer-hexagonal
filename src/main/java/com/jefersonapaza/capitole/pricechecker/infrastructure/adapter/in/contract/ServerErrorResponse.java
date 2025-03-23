package com.jefersonapaza.capitole.pricechecker.infrastructure.adapter.in.contract;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta para errores de servidor")
public class ServerErrorResponse {

    @Schema(description = "Código de error", example = "500")
    private int statusCode;

    @Schema(description = "Mensaje descriptivo del error", example = "Internal Server Error")
    private String message;

    public ServerErrorResponse() {
    }

    public ServerErrorResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}