package com.jefersonapaza.capitole.pricechecker.infrastructure.adapter.in.contract;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Author: Jeferson Apaza
 * Date: 2025-03-22
 * Role: Software Engineer
 * <p>
 * REST contract model representing the structure of the Price response returned by the API.
 * This DTO is used to expose pricing information through the HTTP layer.
 */

@Data
@Builder
@AllArgsConstructor
@Schema(description = "Representa la respuesta con la información del precio")
public class PriceRestResponse {

  @Schema(description = "ID del producto")
  @JsonProperty("product_id")
  private Long productId;

  @Schema(description = "ID de la marca")
  @JsonProperty("brand_id")
  private Long brandId;

  @Schema(description = "ID de la tarifa aplicada")
  @JsonProperty("price_list")
  private Long priceList;

  @Schema(description = "Prioridad de la tarifa")
  private Integer priority;

  @Schema(description = "Precio final aplicado")
  private BigDecimal price;

  @Schema(description = "Moneda del precio", example = "EUR")
  private String currency;

  @Schema(description = "Fecha de inicio de validez del precio", example = "2020-06-14T00:00:00")
  @JsonProperty("start_date")
  private LocalDateTime startDate;

  @Schema(description = "Fecha de fin de validez del precio", example = "2020-12-31T23:59:59")
  @JsonProperty("end_date")
  private LocalDateTime endDate;

}
