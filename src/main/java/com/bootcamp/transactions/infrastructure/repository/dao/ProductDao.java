package com.bootcamp.transactions.infrastructure.repository.dao;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document("product")
public class ProductDao {
    @Id
    private String id;
    private String type;
    private List<String> clientIds;
}

