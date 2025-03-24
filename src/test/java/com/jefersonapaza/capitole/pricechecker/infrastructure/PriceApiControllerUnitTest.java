package com.jefersonapaza.capitole.pricechecker.infrastructure;


import com.jefersonapaza.capitole.pricechecker.application.dto.PriceResponseDto;
import com.jefersonapaza.capitole.pricechecker.application.usecase.GetPriceUseCase;
import com.jefersonapaza.capitole.pricechecker.infrastructure.adapter.in.contract.PriceResponse;
import com.jefersonapaza.capitole.pricechecker.infrastructure.adapter.in.controller.PriceApiController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Optional;

import com.jefersonapaza.capitole.pricechecker.infrastructure.adapter.in.mapper.PriceMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class PriceApiControllerUnitTest {

  @InjectMocks
  private PriceApiController priceApiController;

  @Mock
  private GetPriceUseCase getPriceUseCase;

  @Mock
  private PriceMapper priceMapper;


  @Test
  void givenValidParams_whenGetPrice_thenReturnsOkResponse() {

    // Given
    Long productId = 35455L;
    Long brandId = 1L;
    LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0, 0);

    PriceResponseDto dto = PriceResponseDto.builder()
              .productId(productId)
              .brandId(brandId)
              .priceList(1L)
              .priority(0)
              .price(BigDecimal.valueOf(35.50))
              .currency(Currency.getInstance("EUR"))
              .startDate(applicationDate)
              .endDate(applicationDate.plusDays(1))
              .build();

    PriceResponse response = PriceResponse.builder()
              .productId(productId)
              .brandId(brandId)
              .priceList(1L)
              .price(BigDecimal.valueOf(35.50))
              .startDate(applicationDate)
              .endDate(applicationDate.plusDays(1))
              .build();

    when(getPriceUseCase.execute(productId, brandId, applicationDate)).thenReturn(Optional.of(dto));
    when(priceMapper.toResponse(dto)).thenReturn(response);

    // When
    ResponseEntity<PriceResponse> result = priceApiController.getPrice(productId, brandId, applicationDate);

    // Then
    assertEquals(200, result.getStatusCode().value());
    assertNotNull(result.getBody());
    assertEquals(productId, result.getBody().getProductId());
    assertEquals(brandId, result.getBody().getBrandId());
    assertEquals(BigDecimal.valueOf(35.50), result.getBody().getPrice());

  }

    @Test
    void givenValidParams_whenNoPriceFound_thenReturnsNoContent204() {
        // Given
        Long productId = 35455L;
        Long brandId = 1L;
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0, 0);

        when(getPriceUseCase.execute(productId, brandId, applicationDate)).thenReturn(Optional.empty());

        // When
        ResponseEntity<PriceResponse> result = priceApiController.getPrice(productId, brandId, applicationDate);

        // Then
        assertEquals(204, result.getStatusCode().value());
    }

    @Test
    void givenValidParams_whenUseCaseThrowsException_thenReturnsInternalServerError500() {
        // Given
        Long productId = 35455L;
        Long brandId = 1L;
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0, 0);

        when(getPriceUseCase.execute(productId, brandId, applicationDate))
                .thenThrow(new RuntimeException("Unexpected error"));

        // When & Then
        try {
            priceApiController.getPrice(productId, brandId, applicationDate);
        } catch (RuntimeException e) {
            assertEquals("Unexpected error", e.getMessage());
        }
    }

}
