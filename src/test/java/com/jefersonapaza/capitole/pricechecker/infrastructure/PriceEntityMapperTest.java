package com.jefersonapaza.capitole.pricechecker.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.jefersonapaza.capitole.pricechecker.domain.model.Price;
import com.jefersonapaza.capitole.pricechecker.infrastructure.adapter.out.entity.PriceEntity;
import com.jefersonapaza.capitole.pricechecker.infrastructure.adapter.out.mapper.PriceEntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

/**
 * Author: Jeferson Apaza
 * Date: 2025-03-21
 * Role: Software Engineer
 * <p>
 * Unit tests for PriceEntityMapper.
 * Tests the mapping between PriceEntity and Price domain model.
 * Ensures all fields are correctly mapped both ways.
 */

public class PriceEntityMapperTest {

  private PriceEntityMapper mapper;

  @BeforeEach
  void setUp() {
    mapper = new PriceEntityMapper();
  }

  @Test
  void givenPriceDomain_whenMappedToEntity_thenCorrectEntityIsReturned() {

    // given
    Price price = new Price(
       1L,                                     // brandId
              LocalDateTime.now().minusDays(1),       // startDate
              LocalDateTime.now().plusDays(1),        // endDate
              100L,                                   // priceList
              2L,                                     // productId
              1,                                      // priority
              new BigDecimal("50.00"),            // price
              Currency.getInstance("EUR") // currency
    );

    // when
    PriceEntity entity = mapper.toEntity(price);

    // then
    assertThat(entity.getProductId()).isEqualTo(price.getProductId());
    assertThat(entity.getBrandId()).isEqualTo(price.getBrandId());
    assertThat(entity.getPriceList()).isEqualTo(price.getPriceList());
    assertThat(entity.getPriority()).isEqualTo(price.getPriority());
    assertThat(entity.getStartDate()).isEqualTo(price.getStartDate());
    assertThat(entity.getEndDate()).isEqualTo(price.getEndDate());
    assertThat(entity.getCurrency()).isEqualTo(price.getCurrency().getCurrencyCode());


  }

  @Test
  void givenPriceEntity_whenMappedToDomain_thenCorrectDomainObjectIsReturned() {
    // given
    PriceEntity entity = PriceEntity.builder()
            .brandId(1L)
            .startDate(LocalDateTime.now().minusDays(1))
            .endDate(LocalDateTime.now().plusDays(1))
            .priceList(100L)
            .productId(2L)
            .priority(1)
            .price(new BigDecimal("50.00"))
            .currency("EUR")
            .build();

    // when
    Price price = mapper.toDomain(entity);

    // then
    assertEquals(entity.getBrandId(), price.getBrandId());
    assertEquals(entity.getStartDate(), price.getStartDate());
    assertEquals(entity.getEndDate(), price.getEndDate());
    assertEquals(entity.getPriceList(), price.getPriceList());
    assertEquals(entity.getProductId(), price.getProductId());
    assertEquals(entity.getPriority(), price.getPriority());
    assertEquals(entity.getPrice(), price.getPrice());
    assertEquals(entity.getCurrency(), price.getCurrency().getCurrencyCode());
  }

}
