package com.jefersonapaza.capitole.pricechecker.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

/**
 * Author: Jeferson Apaza
 * Date: 2025-03-21
 * Role: Software Engineer
 * <p>
 * Data Transfer Object used in the Application layer to expose Price information.
 */

@Data
@Builder
@AllArgsConstructor
public class PriceResponseDto {

    private Long productId;
    private Long brandId;
    private Long priceList;
    private Integer priority;
    private BigDecimal price;
    private Currency currency;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}