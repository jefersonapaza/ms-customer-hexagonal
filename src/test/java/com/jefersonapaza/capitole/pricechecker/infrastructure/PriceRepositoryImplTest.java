package com.jefersonapaza.capitole.pricechecker.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import com.jefersonapaza.capitole.pricechecker.domain.model.Price;
import com.jefersonapaza.capitole.pricechecker.domain.service.PriceSelectionService;
import com.jefersonapaza.capitole.pricechecker.domain.service.PriceSelectionServiceImpl;
import com.jefersonapaza.capitole.pricechecker.infrastructure.adapter.out.PriceRepositoryImpl;
import com.jefersonapaza.capitole.pricechecker.infrastructure.adapter.out.entity.PriceEntity;
import com.jefersonapaza.capitole.pricechecker.infrastructure.adapter.out.mapper.PriceEntityMapper;
import com.jefersonapaza.capitole.pricechecker.infrastructure.adapter.out.repository.JpaPriceRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Currency;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;


public class PriceRepositoryImplTest {

  private JpaPriceRepository jpaRepository;
  private PriceEntityMapper mapper;
  private PriceRepositoryImpl repository;
  private PriceSelectionService service;

  static Stream<Arguments> providePricesForBestPriceSelection() {

    LocalDateTime referenceDate = LocalDateTime.of(2023, 3, 19, 12, 0);

    return Stream.of(
            // Caso 1: Solo un price dentro del rango
            Arguments.of(
                    List.of(
                            price(1L, referenceDate.minusDays(5), referenceDate.minusDays(1), 1), // fuera de rango
                            price(2L, referenceDate.minusDays(1), referenceDate.plusDays(1), 1)   // válido
                    ),
                    referenceDate,
                    Optional.of(2L)
            ),

            // Caso 2: Dos prices en rango, diferente prioridad
            Arguments.of(
                    List.of(
                            price(1L, referenceDate.minusDays(1), referenceDate.plusDays(1), 2), // menor prioridad
                            price(2L, referenceDate.minusDays(1), referenceDate.plusDays(1), 5)  // mayor prioridad
                    ),
                    referenceDate,
                    Optional.of(2L)
            ),

            // Caso 3: Dos prices misma prioridad, uno fuera de rango
            Arguments.of(
                    List.of(
                            price(1L, referenceDate.minusDays(5), referenceDate.minusDays(1), 5), // fuera de rango
                            price(2L, referenceDate.minusDays(1), referenceDate.plusDays(1), 5)   // válido
                    ),
                    referenceDate,
                    Optional.of(2L)
            ),

            // Caso 4: Ningún price dentro del rango
            Arguments.of(
                    List.of(
                            price(1L, referenceDate.minusDays(5), referenceDate.minusDays(2), 5), // fuera de rango
                            price(2L, referenceDate.plusDays(2), referenceDate.plusDays(5), 5)    // fuera de rango
                    ),
                    referenceDate,
                    Optional.empty()
            ),

            // Caso 5: Varios en rango con misma prioridad (elige cualquiera del rango)
            Arguments.of(
                    List.of(
                            price(1L, referenceDate.minusDays(1), referenceDate.plusDays(1), 5),
                            price(2L, referenceDate.minusDays(1), referenceDate.plusDays(1), 5)
                    ),
                    referenceDate,
                    Optional.of(1L) // o 2L dependiendo de implementación
            )
    );
  }


  @BeforeEach
  void setUp() {
    jpaRepository = mock(JpaPriceRepository.class);
    mapper = new PriceEntityMapper();
    service = new PriceSelectionServiceImpl();
    repository = new PriceRepositoryImpl(jpaRepository, mapper, service);
  }

  @Test
  void givenProductAndBrand_whenFindPrices_thenReturnsMappedPrices() {

    // given
    Long productId = 100L;
    Long brandId = 1L;
    LocalDateTime date = LocalDateTime.now();

    PriceEntity entity = PriceEntity.builder()
                .brandId(brandId)
                .productId(productId)
                .priceList(1L)
                .priority(1)
                .startDate(date.minusDays(1))
                .endDate(date.plusDays(1))
                .price(new java.math.BigDecimal("50.00"))
                .currency("EUR")
                .build();

    when(jpaRepository.findByProductIdAndBrandId(anyLong(), anyLong()))
                .thenReturn(Collections.singletonList(entity));

    // when
    Optional<Price> optionalPrice = repository.findApplicablePrice(productId, brandId, date);

    // then
    assertThat(optionalPrice).isPresent();
    Price result = optionalPrice.get();
    assertThat(result.getProductId()).isEqualTo(productId);
    assertThat(result.getBrandId()).isEqualTo(brandId);

  }

  @Test
  void givenNoResults_whenFindApplicablePrice_thenReturnsEmptyOptional() {

    // given
    when(jpaRepository.findByProductIdAndBrandId(anyLong(), anyLong()))
                .thenReturn(Collections.emptyList());

    // when
    Optional<Price> price = repository.findApplicablePrice(100L, 1L, LocalDateTime.now());

    // then
    assertThat(price).isEmpty();
    verify(jpaRepository, times(1)).findByProductIdAndBrandId(anyLong(), anyLong());


  }



  private static Price price(Long id, LocalDateTime start, LocalDateTime end, Integer priority) {
    return new Price(
            1L, // brandId
            start,
            end,
            100L, // priceList
            id, // productId
            priority,
            new BigDecimal("50.00"),
            Currency.getInstance("EUR")
    );
  }

  @ParameterizedTest
  @MethodSource("providePricesForBestPriceSelection")
  void givenPrices_whenSelectBestPrice_thenReturnsExpectedPrice(List<Price> prices, LocalDateTime date, Optional<Long> expectedProductId) {
    // when
    Optional<Price> result = service.selectBestApplicablePrice(prices, date);

    // then
    if (expectedProductId.isPresent()) {
      assertThat(result).isPresent();
      assertThat(result.get().getProductId()).isEqualTo(expectedProductId.get());
    } else {
      assertThat(result).isEmpty();
    }
  }


}
