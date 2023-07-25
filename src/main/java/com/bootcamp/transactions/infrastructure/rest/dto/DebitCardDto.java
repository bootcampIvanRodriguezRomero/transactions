package com.bootcamp.transactions.infrastructure.rest.dto;

import java.time.LocalDate;
import lombok.Data;
import reactor.core.publisher.Flux;

@Data
public class DebitCardDto {
  private String id;
  private String cardNumber;
  private LocalDate expirationDate;
  private String cvv;
  private ProductDto mainProduct;
  private Flux<ProductDto> products;
}
