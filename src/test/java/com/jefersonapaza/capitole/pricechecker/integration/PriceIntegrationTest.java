package com.jefersonapaza.capitole.pricechecker.integration;

import static org.assertj.core.api.Assertions.assertThat;

import com.jefersonapaza.capitole.pricechecker.PriceCheckerApplication;
import com.jefersonapaza.capitole.pricechecker.infrastructure.adapter.in.contract.PriceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.stream.Stream;

@SpringBootTest(classes = PriceCheckerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PriceIntegrationTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;


  static Stream<TestCase> provideTestCases() {
    return Stream.of(
           new TestCase("2020-06-14 10:00:00", 35455L, 1L,new BigDecimal("35.50")),
           new TestCase("2020-06-14 16:00:00", 35455L, 1L,new BigDecimal("25.45")),
           new TestCase("2020-06-14 21:00:00", 35455L, 1L,new BigDecimal("35.50")),
           new TestCase("2020-06-15 10:00:00", 35455L, 1L,new BigDecimal("30.50")),
           new TestCase("2020-06-16 21:00:00", 35455L, 1L,new BigDecimal("38.95"))
    );
  }

  @ParameterizedTest
  @MethodSource("provideTestCases")
  void givenValidRequest_whenGetPrice_thenReturnsExpectedPrice(TestCase testCase) {
      // Arrange
      String baseUrl = "http://localhost:" + port + "/price";
      String formattedDate = testCase.applicationDate;

      String url = baseUrl + "?productId=" + testCase.productId
              + "&brandId=" + testCase.brandId
              + "&applicationDate=" + formattedDate;

      // Act
      ResponseEntity<PriceResponse> response = restTemplate.getForEntity(url, PriceResponse.class);
      // Assert
      assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
      assertThat(response.getBody()).isNotNull();
      assertThat(response.getBody().getPrice()).isEqualTo(testCase.expectedPrice);

  }

  // Clase estática auxiliar para parametrizar los tests
  private static class TestCase {
    String applicationDate;
    Long productId;
    Long brandId;
    BigDecimal expectedPrice;

    TestCase(String applicationDate, Long productId, Long brandId, BigDecimal expectedPrice) {
      this.applicationDate = applicationDate;
      this.productId = productId;
      this.brandId = brandId;
      this.expectedPrice = expectedPrice;
    }
  }

}
