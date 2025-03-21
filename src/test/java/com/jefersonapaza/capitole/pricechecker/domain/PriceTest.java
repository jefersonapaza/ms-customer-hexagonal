package com.jefersonapaza.capitole.pricechecker.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

import com.jefersonapaza.capitole.pricechecker.domain.model.Price;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;


/**
 * Author: Jeferson Apaza
 * Date: 2025-03-20
 * Role: Software Engineer
 * <p>
 * Unit tests for the Price domain entity, focusing on business rules like applicability within date ranges.
 */
public class PriceTest {

  static Stream<Arguments> providePriceScenarios() {
        return Stream.of(
                // Caso donde la fecha está dentro del rango de vigencia
                Arguments.of(
                        LocalDateTime.of(2025, 3, 1, 10, 0),
                        LocalDateTime.of(2025, 3, 31, 18, 0),
                        LocalDateTime.of(2025, 3, 20, 12, 0),
                        true
                ),
                // Caso donde la fecha es anterior al rango
                Arguments.of(
                        LocalDateTime.of(2025, 3, 10, 10, 0),
                        LocalDateTime.of(2025, 3, 20, 18, 0),
                        LocalDateTime.of(2025, 3, 5, 9, 0),
                        false
                ),
                // Caso donde la fecha es posterior al rango
                Arguments.of(
                        LocalDateTime.of(2025, 3, 1, 10, 0),
                        LocalDateTime.of(2025, 3, 10, 18, 0),
                        LocalDateTime.of(2025, 3, 15, 9, 0),
                        false
                ),
                // Caso donde la fecha es igual a startDate
                Arguments.of(
                        LocalDateTime.of(2025, 3, 15, 10, 0),
                        LocalDateTime.of(2025, 3, 30, 18, 0),
                        LocalDateTime.of(2025, 3, 15, 10, 0),
                        true
                ),
                // Caso donde la fecha es igual a endDate
                Arguments.of(
                        LocalDateTime.of(2025, 3, 1, 10, 0),
                        LocalDateTime.of(2025, 3, 20, 18, 0),
                        LocalDateTime.of(2025, 3, 20, 18, 0),
                        true
                )
        );
  }

  @ParameterizedTest
  @MethodSource("providePriceScenarios")
  void givenDateRangeAndCurrentDate_whenIsApplicable_thenReturnsExpectedResult(LocalDateTime startDate,
                                                                                 LocalDateTime endDate,
                                                                                 LocalDateTime currentDate,
                                                                                 Boolean expectedResult) {
      Price price = new Price(
                1L,                                // brandId
                startDate,
                endDate,
                100L,                             // priceList
                200L,                                     // productId
                1,                                        // priority
                BigDecimal.valueOf(99.99),                // price
                Currency.getInstance("USD")   // currency
      );

      Boolean actualResult = price.isApplicable(currentDate);
      assertEquals(expectedResult, actualResult);

  }

  @Test
  void givenEqualPrices_whenEquals_thenReturnsTrue() {

    Price price1 = new Price(1L, LocalDateTime.now(), LocalDateTime.now().plusDays(1), 1L,
                        35455L, 0, BigDecimal.valueOf(35.50), Currency.getInstance("EUR"));
    Price price2 = new Price(1L, price1.getStartDate(), price1.getEndDate(), 1L, 35455L,
                              0, BigDecimal.valueOf(35.50), Currency.getInstance("EUR"));

    assertThat(price1).isEqualTo(price2);

  }

  @Test
  void givenEqualPrices_whenHashCode_thenSameHash() {

    Price price1 = new Price(1L, LocalDateTime.now(), LocalDateTime.now().plusDays(1), 1L,
                        35455L, 0, BigDecimal.valueOf(35.50), Currency.getInstance("EUR"));
    Price price2 = new Price(1L, price1.getStartDate(), price1.getEndDate(), 1L, 35455L,
                              0, BigDecimal.valueOf(35.50), Currency.getInstance("EUR"));

    assertThat(price1.hashCode()).isEqualTo(price2.hashCode());

  }


}
