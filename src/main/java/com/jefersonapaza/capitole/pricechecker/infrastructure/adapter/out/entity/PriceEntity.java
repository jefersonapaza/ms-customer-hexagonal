package com.jefersonapaza.capitole.pricechecker.infrastructure.adapter.out.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

/**
 * Author: Jeferson Apaza
 * Date: 2025-03-21
 * Role: Software Engineer
 * <p>
 * JPA entity representing the database structure for storing product pricing information.
 */
@Entity
@Table(name = "PRICES")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "BRAND_ID", nullable = false)
  private Long brandId;

  @Column(name = "START_DATE", nullable = false)
  private LocalDateTime startDate;

  @Column(name = "END_DATE", nullable = false)
  private LocalDateTime endDate;

  @Column(name = "PRICE_LIST", nullable = false)
  private Long priceList;

  @Column(name = "PRODUCT_ID", nullable = false)
  private Long productId;

  @Column(name = "PRIORITY", nullable = false)
  private Integer priority;

  @Column(name = "PRICE", nullable = false)
  private BigDecimal price;

  @Column(name = "CURR", nullable = false)
  private Currency currency;


}
