package com.jefersonapaza.capitole.pricechecker.infrastructure.adapter.in.mapper;

import com.jefersonapaza.capitole.pricechecker.application.dto.PriceResponseDto;
import com.jefersonapaza.capitole.pricechecker.infrastructure.adapter.in.contract.PriceResponse;
import org.springframework.stereotype.Component;

@Component
public class PriceMapper {

    public PriceResponse toResponse(PriceResponseDto dto) {
        return PriceResponse.builder()
                .price(dto.getPrice())
                .brandId(dto.getBrandId())
                .productId(dto.getProductId())
                .currency(dto.getCurrency().getCurrencyCode())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .priceList(dto.getPriceList())
                .priority(dto.getPriority())
                .build();
    }
}
