package com.bootcamp.transactions.infrastructure.repository;

import com.bootcamp.transactions.infrastructure.repository.dao.ClientDao;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
public interface ClientRepository extends ReactiveMongoRepository<ClientDao,String> {
}
