package com.bootcamp.transactions.infrastructure.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class ProductDto {
    @JsonProperty("identification")
    @Id
    private String id;
    @NotBlank(message = "Type is required")
    private String type;
    private double balance;
}

