package com.bootcamp.transactions.infrastructure.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import lombok.Data;
import org.springframework.data.annotation.Id;
import reactor.core.publisher.Flux;

@Data
public class ClientDto {
  @JsonProperty("identification")
  @Id
  private String id;
  @NotBlank(message = "document type is required")
  private String documentType;
  @NotBlank(message = "document number is required")
  private String documentNumber;
  @NotBlank(message = "full name is required")
  private String fullName;
  @NotBlank(message = "email is required")
  @Email
  private String email;
  @NotBlank(message = "type is required")
  private String type;
  private Flux<ProductDto> products;
  private Flux<DebitCardDto> debitCards;
}
