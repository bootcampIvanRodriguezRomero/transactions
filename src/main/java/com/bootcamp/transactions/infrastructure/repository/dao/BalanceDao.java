package com.bootcamp.transactions.infrastructure.repository.dao;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("balance")
public class BalanceDao {
    @Id
    private String id;
    private String productId;
    private double balance;
}
