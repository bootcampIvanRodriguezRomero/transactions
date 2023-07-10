package com.bootcamp.transactions.infrastructure.rest.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;
@Data
public class TransactionDto {
    @Id
    private String id;
    private Date date;
    @NotBlank(message = "Amount is required")
    private double amount;
}
