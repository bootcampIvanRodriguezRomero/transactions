package com.bootcamp.transactions.infrastructure.repository;

import com.bootcamp.transactions.infrastructure.repository.dao.ProductDao;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProductRepository extends ReactiveMongoRepository<ProductDao, String> {
}
