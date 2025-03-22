package com.jefersonapaza.capitole.pricechecker.application.usecase;

import com.jefersonapaza.capitole.pricechecker.domain.model.Price;
import com.jefersonapaza.capitole.pricechecker.domain.port.PriceRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

/**
 * Author: Jeferson Apaza
 * Date: 2025-03-21
 * Role: Software Engineer
 * <p>
 * Application use case to retrieve the applicable price based on business rules,
 * orchestrating the domain logic and infrastructure access for price selection.
 */

@RequiredArgsConstructor
public class GetPriceUseCase {

  private final PriceRepository priceRepository;

  public Optional<Price> execute(Long productId, Long brandId, LocalDateTime date) {
    return priceRepository.findApplicablePrice(productId, brandId, date);
  }

}
