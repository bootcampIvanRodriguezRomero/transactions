package com.bootcamp.transactions.infrastructure.rest.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.Id;
@Data
public class TransactionTypeDto {
    @JsonProperty("identification")
    @Id
    private String id;
    @NotBlank(message = "Name is required")
    private String name;
}
