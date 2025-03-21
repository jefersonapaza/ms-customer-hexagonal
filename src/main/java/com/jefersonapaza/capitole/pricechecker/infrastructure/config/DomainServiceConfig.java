package com.jefersonapaza.capitole.pricechecker.infrastructure.config;

import com.jefersonapaza.capitole.pricechecker.domain.service.PriceSelectionService;
import com.jefersonapaza.capitole.pricechecker.domain.service.PriceSelectionServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainServiceConfig {

    @Bean
    public PriceSelectionService priceSelectionService() {
        return new PriceSelectionServiceImpl();
    }
}
