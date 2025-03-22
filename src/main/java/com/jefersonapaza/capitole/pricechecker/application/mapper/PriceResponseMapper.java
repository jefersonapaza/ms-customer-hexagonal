package com.jefersonapaza.capitole.pricechecker.application.mapper;

import com.jefersonapaza.capitole.pricechecker.application.dto.PriceResponseDto;
import com.jefersonapaza.capitole.pricechecker.domain.model.Price;
import org.springframework.stereotype.Component;

/**
 * Author: Jeferson Apaza
 * Date: 2025-03-21
 * Role: Software Engineer
 * <p>
 * Mapper to convert Price domain model into PriceResponseDto used in the Application layer.
 */
@Component
public class PriceResponseMapper {

    public PriceResponseDto toDto(Price price) {
        return new PriceResponseDto(
                price.getProductId(),
                price.getBrandId(),
                price.getPriceList(),
                price.getPriority(),
                price.getPrice(),
                price.getCurrency(),
                price.getStartDate(),
                price.getEndDate()
        );
    }
}