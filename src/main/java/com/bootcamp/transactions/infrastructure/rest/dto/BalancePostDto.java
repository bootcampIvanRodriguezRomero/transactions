package com.bootcamp.transactions.infrastructure.rest.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class BalancePostDto {
  @Id
  private String id;
  private String productId;
  @NotBlank(message = "Balance is required")
  private double balance;
}
