package com.jefersonapaza.capitole.pricechecker.domain.port;

import com.jefersonapaza.capitole.pricechecker.domain.model.Price;
import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceRepository {
  Optional<Price> findApplicablePrice(Long productId, Long brandId, LocalDateTime applicationDate);
}
