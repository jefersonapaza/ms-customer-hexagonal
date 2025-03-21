package com.jefersonapaza.capitole.pricechecker.domain.service;

import com.jefersonapaza.capitole.pricechecker.domain.model.Price;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@FunctionalInterface
public interface PriceSelectionService {
  Optional<Price> selectBestApplicablePrice(List<Price> prices, LocalDateTime applicationDate);
}
