package com.jefersonapaza.capitole.pricechecker.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Objects;

/**
 * Author: Jeferson Apaza
 * Date: 2025-03-20
 * Role: Software Engineer
 * <p>
 * Domain entity representing product pricing for a brand within a defined time range.
 */
public class Price {

  private final Long brandId;
  private final LocalDateTime startDate;
  private final LocalDateTime endDate;
  private final Long priceList;
  private final Long productId;
  private final Integer priority;
  private final BigDecimal price;
  private final Currency currency;

  public Price(Long brandId, LocalDateTime startDate, LocalDateTime endDate, Long priceList, Long productId,
               Integer priority, BigDecimal price, Currency currency) {

    this.brandId = brandId;
    this.startDate = startDate;
    this.endDate = endDate;
    this.priceList = priceList;
    this.productId = productId;
    this.priority = priority;
    this.price = price;
    this.currency = currency;
  }

  public Long getBrandId() {
    return brandId;
  }

  public Long getPriceList() {
    return priceList;
  }

  public Long getProductId() {
    return productId;
  }

  public Integer getPriority() {
    return priority;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public Currency getCurrency() {
    return currency;
  }

  public LocalDateTime getStartDate() {
    return startDate;
  }

  public LocalDateTime getEndDate() {
    return endDate;
  }

  public Boolean isApplicable(LocalDateTime applicationDate) {
    return !applicationDate.isBefore(startDate) && !applicationDate.isAfter(endDate);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Price)) return false;
    Price price1 = (Price) o;
     return Objects.equals(brandId, price1.brandId) &&
            Objects.equals(priceList, price1.priceList) &&
            Objects.equals(productId, price1.productId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(brandId, priceList, productId);
  }

}
