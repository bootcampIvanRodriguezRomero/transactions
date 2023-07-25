package com.bootcamp.transactions.infrastructure.repository;

import com.bootcamp.transactions.infrastructure.repository.dao.TransactionTypeDao;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TransactionTypeRepository
  extends ReactiveMongoRepository<TransactionTypeDao, String> {
}
