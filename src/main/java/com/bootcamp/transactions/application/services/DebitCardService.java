package com.bootcamp.transactions.application.services;

import com.bootcamp.transactions.infrastructure.rest.dto.DebitCardDto;
import com.bootcamp.transactions.infrastructure.rest.dto.TransactionDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DebitCardService {
  Flux<DebitCardDto> getAllDebitCards();
  
  Mono<DebitCardDto> getDebitCardById(String id);
  
  Mono<DebitCardDto> createDebitCard(DebitCardDto debitCardDto);
  
  Mono<DebitCardDto> modifyDebitCard(String id, DebitCardDto debitCardDto);
  
  Mono<Void> deleteDebitCard(String id);
  
  Mono<Void> deleteAllDebitCards();
  Flux<TransactionDto> getDebitCardTransactions(String debitCardId);
}
