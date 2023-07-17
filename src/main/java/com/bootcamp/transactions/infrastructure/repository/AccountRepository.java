package com.bootcamp.transactions.infrastructure.repository;

import com.bootcamp.transactions.infrastructure.repository.dao.AccountDao;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface AccountRepository extends ReactiveMongoRepository<AccountDao, String> {
  Flux<AccountDao> findByCustomerId(String customerId);
}
