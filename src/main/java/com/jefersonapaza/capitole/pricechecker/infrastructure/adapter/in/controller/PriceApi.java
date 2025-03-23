package com.jefersonapaza.capitole.pricechecker.infrastructure.adapter.in.controller;

import com.jefersonapaza.capitole.pricechecker.infrastructure.adapter.in.contract.PriceResponse;
import com.jefersonapaza.capitole.pricechecker.infrastructure.adapter.in.contract.ServerErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

@Validated
@RestController
@Tag(name = "Price", description = "Price API")
public interface PriceApi {

    @Operation(
            operationId = "getPrice",
            summary = "Retrieve product price",
            description = "Returns the price of a given product based on product ID and date.",
            tags = { "Price" },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful Operation", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = PriceResponse.class))
                    }),
                    @ApiResponse(responseCode = "204", description = "No Content, no se encontró información para los parámetros especificados", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ServerErrorResponse.class))
                    })
            }
    )
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/prices",
            produces = { "application/json" }
    )
    @ResponseStatus(HttpStatus.OK)
    PriceResponse getPrice(
            @NotNull @Parameter(name = "productId", description = "ID of the product.", required = true, in = ParameterIn.QUERY) @RequestParam("productId") Long productId,
            @NotNull @Parameter(name = "brandId", description = "ID of the brand.", required = true, in = ParameterIn.QUERY) @RequestParam("brandId") Long brandId,
            @NotNull @Parameter(name = "applicationDate", description = "Date of application (yyyy-MM-dd HH:mm:ss).", required = true, in = ParameterIn.QUERY) @RequestParam("applicationDate") String applicationDate,
            @Parameter(hidden = true) final ServerWebExchange exchange
    );

}
