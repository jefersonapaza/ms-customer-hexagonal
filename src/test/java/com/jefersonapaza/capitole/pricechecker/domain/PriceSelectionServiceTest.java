package com.jefersonapaza.capitole.pricechecker.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.jefersonapaza.capitole.pricechecker.domain.model.Price;
import com.jefersonapaza.capitole.pricechecker.domain.service.PriceSelectionService;
import com.jefersonapaza.capitole.pricechecker.domain.service.PriceSelectionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

public class PriceSelectionServiceTest {

  private PriceSelectionService service;

  @BeforeEach
  void setUp() {
    service = new PriceSelectionServiceImpl();
  }

  @Test
  void givenApplicablePrices_whenSelectBest_thenReturnsPriceWithHighestPriority() {
    // given
    LocalDateTime applicationDate = LocalDateTime.now();
    Price lowPriority = new Price(1L, applicationDate.minusDays(1), applicationDate.plusDays(1),
                1L, 100L, 0, new BigDecimal("10.00"), Currency.getInstance("EUR"));
    Price highPriority = new Price(1L, applicationDate.minusDays(1), applicationDate.plusDays(1),
                2L, 100L, 1, new BigDecimal("20.00"), Currency.getInstance("EUR"));

    List<Price> prices = List.of(lowPriority, highPriority);

    // when
    Optional<Price> result = service.selectBestApplicablePrice(prices, applicationDate);

    // then
    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(highPriority);

  }

  @Test
  void givenNoApplicablePrices_whenSelectBest_thenReturnsEmptyOptional() {

    // given
    LocalDateTime applicationDate = LocalDateTime.now();
    Price expiredPrice = new Price(1L, applicationDate.minusDays(10), applicationDate.minusDays(5),
            1L, 100L, 1, new BigDecimal("10.00"), Currency.getInstance("EUR"));

    List<Price> prices = List.of(expiredPrice);

    // when
    Optional<Price> result = service.selectBestApplicablePrice(prices, applicationDate);

    // then
    assertThat(result).isEmpty();
  }


}
