package com.bootcamp.transactions.infrastructure.repository.dao;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document("transaction")
public class TransactionDao {
    @Id
    private String id;
    private Date date;
    private double amount;
}
