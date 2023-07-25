package com.bootcamp.transactions.infrastructure.rest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AmountDto {
  @NotNull(message = "Amount is required")
  private double amount;
}
