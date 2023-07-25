package com.bootcamp.transactions.infrastructure.rest.dto;

import java.util.List;
import lombok.Data;

@Data
public class ClientSummaryDto {
  private ClientDto client;
  private List<ProductDto> products;
  private List<DebitCardDto> debitCards;
}

