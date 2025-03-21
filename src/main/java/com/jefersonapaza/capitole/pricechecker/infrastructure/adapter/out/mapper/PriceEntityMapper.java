package com.jefersonapaza.capitole.pricechecker.infrastructure.adapter.out.mapper;

import com.jefersonapaza.capitole.pricechecker.domain.model.Price;
import com.jefersonapaza.capitole.pricechecker.infrastructure.adapter.out.entity.PriceEntity;
import org.springframework.stereotype.Component;

import java.util.Currency;

/**
 * Author: Jeferson Apaza
 * Date: 2025-03-21
 * Role: Software Engineer
 * <p>
 * Mapper responsible for converting between Price (domain model) and PriceEntity (JPA entity).
 */
@Component
public class PriceEntityMapper {

  public PriceEntity toEntity(Price price) {
    return PriceEntity.builder()
            .brandId(price.getBrandId())
            .startDate(price.getStartDate())
            .endDate(price.getEndDate())
            .priceList(price.getPriceList())
            .productId(price.getProductId())
            .priority(price.getPriority())
            .price(price.getPrice())
            .currency(price.getCurrency().getCurrencyCode())
            .build();
  }

  public Price toDomain(PriceEntity entity) {
    return new Price(
            entity.getBrandId(),
            entity.getStartDate(),
            entity.getEndDate(),
            entity.getPriceList(),
            entity.getProductId(),
            entity.getPriority(),
            entity.getPrice(),
            Currency.getInstance(entity.getCurrency())
    );
  }

}
