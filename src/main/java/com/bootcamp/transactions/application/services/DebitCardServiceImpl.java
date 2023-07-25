package com.bootcamp.transactions.application.services;

import com.bootcamp.transactions.infrastructure.rest.dto.DebitCardDto;
import com.bootcamp.transactions.infrastructure.rest.dto.TransactionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class DebitCardServiceImpl implements DebitCardService {
  
  private final WebClient.Builder webClientBuilder;
  private final String debitCardServiceUrl = "http://debitcard/debitcards";
  
  @Override
  public Flux<DebitCardDto> getAllDebitCards() {
    return webClientBuilder.build()
      .get()
      .uri(debitCardServiceUrl)
      .retrieve()
      .bodyToFlux(DebitCardDto.class);
  }
  
  @Override
  public Mono<DebitCardDto> getDebitCardById(String id) {
    return webClientBuilder.build()
      .get()
      .uri(debitCardServiceUrl + "/{id}", id)
      .retrieve()
      .bodyToMono(DebitCardDto.class);
  }
  
  @Override
  public Mono<DebitCardDto> createDebitCard(DebitCardDto debitCardDto) {
    return webClientBuilder.build()
      .post()
      .uri(debitCardServiceUrl)
      .bodyValue(debitCardDto)
      .retrieve()
      .bodyToMono(DebitCardDto.class);
  }
  
  @Override
  public Mono<DebitCardDto> modifyDebitCard(String id, DebitCardDto debitCardDto) {
    return webClientBuilder.build()
      .put()
      .uri(debitCardServiceUrl + "/{id}", id)
      .bodyValue(debitCardDto)
      .retrieve()
      .bodyToMono(DebitCardDto.class);
  }
  
  @Override
  public Mono<Void> deleteDebitCard(String id) {
    return webClientBuilder.build()
      .delete()
      .uri(debitCardServiceUrl + "/{id}", id)
      .retrieve()
      .bodyToMono(Void.class);
  }
  
  @Override
  public Mono<Void> deleteAllDebitCards() {
    return webClientBuilder.build()
      .delete()
      .uri(debitCardServiceUrl)
      .retrieve()
      .bodyToMono(Void.class);
  }
  @Override
  public Flux<TransactionDto> getDebitCardTransactions(String debitCardId) {
    return webClientBuilder.build()
      .get()
      .uri(debitCardServiceUrl + "/{id}/transactions", debitCardId)
      .retrieve()
      .bodyToFlux(TransactionDto.class);
  }
}
