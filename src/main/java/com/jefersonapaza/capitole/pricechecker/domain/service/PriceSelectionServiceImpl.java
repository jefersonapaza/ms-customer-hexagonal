package com.jefersonapaza.capitole.pricechecker.domain.service;

import com.jefersonapaza.capitole.pricechecker.domain.model.Price;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class PriceSelectionServiceImpl implements PriceSelectionService {

  @Override
  public Optional<Price> selectBestApplicablePrice(List<Price> prices, LocalDateTime applicationDate) {
    return prices.stream()
              .filter(price -> price.isApplicable(applicationDate))
              .max(Comparator.comparing(Price::getPriority));
  }
}
