package com.jefersonapaza.capitole.pricechecker.infrastructure.adapter.out;

import com.jefersonapaza.capitole.pricechecker.domain.model.Price;
import com.jefersonapaza.capitole.pricechecker.domain.port.PriceRepository;
import com.jefersonapaza.capitole.pricechecker.infrastructure.adapter.out.mapper.PriceEntityMapper;
import com.jefersonapaza.capitole.pricechecker.infrastructure.adapter.out.repository.JpaPriceRepository;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


/**
 * Author: Jeferson Apaza
 * Date: 2025-03-21
 * Role: Software Engineer
 * <p>
 * Adapter implementation of PriceRepository for persistence, using JPA to access and manage Price entities.
 */

@RequiredArgsConstructor
@Repository
public class PriceRepositoryImpl implements PriceRepository {

  private final JpaPriceRepository jpaPriceRepository;
  private final PriceEntityMapper mapper;

  @Override
  public Optional<Price> findApplicablePrice(Long productId, Long brandId, LocalDateTime applicationDate) {
      return jpaPriceRepository.findByProductIdAndBrandId(productId, brandId)
              .stream()
              .map(mapper::toDomain)
              .filter(price -> price.isApplicable(applicationDate))
              .max(Comparator.comparing(Price::getPriority));

  }
}
