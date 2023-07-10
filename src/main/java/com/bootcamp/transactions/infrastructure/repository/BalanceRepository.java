package com.bootcamp.transactions.infrastructure.repository;
import com.bootcamp.transactions.infrastructure.repository.dao.BalanceDao;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface BalanceRepository extends ReactiveMongoRepository<BalanceDao,String> {
    Mono<BalanceDao> findByProductIdAndClientId(String productId, String clientId);
}
