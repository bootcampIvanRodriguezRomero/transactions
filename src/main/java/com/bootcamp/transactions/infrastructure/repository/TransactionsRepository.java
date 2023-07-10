package com.bootcamp.transactions.infrastructure.repository;
import com.bootcamp.transactions.infrastructure.repository.dao.TransactionDao;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
public interface TransactionsRepository extends ReactiveMongoRepository<TransactionDao,String> {
}
