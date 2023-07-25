package com.bootcamp.transactions.infrastructure.repository.dao;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("transactionType")
public class TransactionTypeDao {
  @Id
  private String id;
  private String name;
}
