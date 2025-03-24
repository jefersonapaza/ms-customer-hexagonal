package com.jefersonapaza.capitole.pricechecker.infrastructure.adapter.in.controller;

import com.jefersonapaza.capitole.pricechecker.application.usecase.GetPriceUseCase;
import com.jefersonapaza.capitole.pricechecker.infrastructure.adapter.in.contract.PriceResponse;
import com.jefersonapaza.capitole.pricechecker.infrastructure.adapter.in.mapper.PriceMapper;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class PriceApiController implements PriceApi {

    private final GetPriceUseCase getPriceUseCase;
    private final PriceMapper priceMapper;

    @Override
    @GetMapping("/price")
    public ResponseEntity<PriceResponse> getPrice(
            @NotNull @RequestParam("productId") Long productId,
            @NotNull @RequestParam("brandId") Long brandId,
            @NotNull @RequestParam("applicationDate")
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime applicationDate
    ) {

      if (productId <= 0 || brandId <= 0) {
            throw new IllegalArgumentException("Product ID and Brand ID must be positive numbers");
      }
      return getPriceUseCase.execute(productId, brandId, applicationDate)
                .map(dto -> ResponseEntity.ok(priceMapper.toResponse(dto)))
                .orElse(ResponseEntity.noContent().build());
    }
}
