package com.jefersonapaza.capitole.pricechecker.infrastructure.adapter.out.repository;

import java.time.LocalDateTime;
import java.util.List;
import com.jefersonapaza.capitole.pricechecker.infrastructure.adapter.out.entity.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface JpaPriceRepository extends JpaRepository<PriceEntity, Long> {
  List<PriceEntity> findByProductIdAndBrandId(Long productId, Long brandId);
}
