package com.bootcamp.transactions.infrastructure.rest.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class TransactionPostDto {
    @Id
    private String id;
    @NotBlank(message = "Amount is required")
    private double amount;
}
