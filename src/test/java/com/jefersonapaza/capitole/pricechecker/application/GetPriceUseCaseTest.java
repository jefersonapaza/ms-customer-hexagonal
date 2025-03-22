package com.jefersonapaza.capitole.pricechecker.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verifyNoInteractions;

import com.jefersonapaza.capitole.pricechecker.application.dto.PriceResponseDto;
import com.jefersonapaza.capitole.pricechecker.application.mapper.PriceResponseMapper;
import com.jefersonapaza.capitole.pricechecker.application.usecase.GetPriceUseCase;
import com.jefersonapaza.capitole.pricechecker.domain.model.Price;
import com.jefersonapaza.capitole.pricechecker.domain.port.PriceRepository;
import com.jefersonapaza.capitole.pricechecker.infrastructure.adapter.out.entity.PriceEntity;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class GetPriceUseCaseTest {

    @Mock
    private PriceRepository priceRepository;

    @Mock
    private PriceResponseMapper priceResponseMapper;

    private PriceResponseMapper priceResponseMapperInit;


    private GetPriceUseCase getPriceUseCase;

    @BeforeEach
    void setUp() {
        priceResponseMapperInit = new PriceResponseMapper();
        getPriceUseCase = new GetPriceUseCase(priceRepository, priceResponseMapperInit);
    }

    @Test
    void givenValidInputs_whenExecute_thenReturnsPriceResponseDto() {
        // given
        Long productId = 35455L;
        Long brandId = 1L;
        LocalDateTime date = LocalDateTime.of(2025, 3, 21, 12, 0); // Fecha fija

        PriceEntity entity = new PriceEntity();
        Price price = new Price(
                brandId,
                date.minusDays(1),
                date.plusDays(1),
                1L,
                productId,
                1,
                BigDecimal.valueOf(100.0),
                Currency.getInstance("EUR")
        );

        when(priceRepository.findApplicablePrice(productId, brandId, date))
                .thenReturn(Optional.of(price));

        // Act
        Optional<PriceResponseDto> result = getPriceUseCase.execute(productId, brandId, date);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(price.getProductId(), result.get().getProductId());
        assertEquals(price.getBrandId(), result.get().getBrandId());
        assertEquals(price.getPrice(), result.get().getPrice());

        // then
        assertTrue(result.isPresent());
        assertEquals(price.getProductId(), result.get().getProductId());
        assertEquals(price.getBrandId(), result.get().getBrandId());
        assertEquals(price.getPrice(), result.get().getPrice());
        assertEquals(price.getCurrency(), result.get().getCurrency());
    }

    @Test
    void givenValidInputs_whenPriceNotFound_thenReturnEmptyOptional() {
        // Arrange
        Long productId = 1L;
        Long brandId = 1L;
        LocalDateTime date = LocalDateTime.now();

        when(priceRepository.findApplicablePrice(productId, brandId, date)).thenReturn(Optional.empty());

        // Act
        Optional<PriceResponseDto> result = getPriceUseCase.execute(productId, brandId, date);

        // Assert
        assertThat(result).isEmpty();
        verify(priceRepository, times(1)).findApplicablePrice(productId, brandId, date);
        verifyNoInteractions(priceResponseMapper);

    }




}
