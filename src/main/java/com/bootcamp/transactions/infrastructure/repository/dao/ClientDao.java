package com.bootcamp.transactions.infrastructure.repository.dao;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document("client")
public class ClientDao {
    @Id
    private String id;
    private String documentType;
    private String documentNumber;
    private String fullName;
    private String email;
    private String type;
    private List<String> productIds;
}
