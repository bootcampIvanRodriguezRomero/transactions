package com.bootcamp.transactions.infrastructure.rest.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class BalanceDto {
  @Id
  private String id;
  private  String clientId;
  private String productId;
  @NotBlank(message = "Balance is required")
  private double balance;
}
