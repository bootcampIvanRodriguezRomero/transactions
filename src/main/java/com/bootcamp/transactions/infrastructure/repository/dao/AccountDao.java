package com.bootcamp.transactions.infrastructure.repository.dao;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "accounts")
public class AccountDao {
  @Id
  private String id;
  private String customerId;
  private String bankId;
  private String productType;
  private double balance;
}
