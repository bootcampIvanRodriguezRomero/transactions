package com.bootcamp.transactions.infrastructure.rest;

import com.bootcamp.transactions.application.services.ClientService;
import com.bootcamp.transactions.application.services.DebitCardService;
import com.bootcamp.transactions.application.services.ProductService;
import com.bootcamp.transactions.infrastructure.rest.dto.ClientDto;
import com.bootcamp.transactions.infrastructure.rest.dto.ClientSummaryDto;
import com.bootcamp.transactions.infrastructure.rest.dto.DebitCardDto;
import com.bootcamp.transactions.infrastructure.rest.dto.ProductDto;
import com.bootcamp.transactions.infrastructure.rest.dto.TransactionDto;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple3;
import com.bootcamp.transactions.infrastructure.rest.dto.BalanceDto;

@RestController
@RequestMapping("/api/clientSummary")
@RequiredArgsConstructor
@Slf4j
public class ClientSummaryResource {
  private final ClientService clientService;
  private final ProductService productService;
  private final DebitCardService debitCardService;
  
  @GetMapping("/{clientId}")
  public Flux<ClientSummaryDto> getClientSummary(@PathVariable String clientId) {
    Mono<ClientDto> clientMono = clientService.getClientById(clientId);
    Flux<ProductDto> productsFlux = clientMono.flatMapMany(clientDto -> clientDto.getProducts());
    Flux<DebitCardDto> debitCardsFlux = clientMono.flatMapMany(clientDto -> clientDto.getDebitCards());
    
    return Flux.zip(clientMono, productsFlux, debitCardsFlux)
      .map(this::fromClientAndProductsAndDebitCardsToClientSummaryDto);
  }
  
  @GetMapping("/{clientId}/creditDebt")
  public Mono<Double> getCreditDebt(@PathVariable String clientId) {
    return clientService.getClientById(clientId)
      .flatMapMany(clientDto -> clientDto.getProducts())
      .filter(productDto -> productDto.getType().equals("Credit"))
      .flatMap(productDto -> productService.getBalanceByProductId(productDto.getId()))
      .map(BalanceDto::getBalance)
      .reduce(0.0, Double::sum);
  }
  
  @GetMapping("/{clientId}/canAcquireProduct")
  public Mono<Boolean> canAcquireProduct(@PathVariable String clientId, @RequestParam String productId) {
    return clientService.getClientById(clientId)
      .flatMapMany(clientDto -> clientDto.getProducts())
      .filter(productDto -> productDto.getType().equals("Credit"))
      .flatMap(productDto -> productService.getBalanceByProductId(productDto.getId()))
      .map(BalanceDto::getBalance)
      .reduce(0.0, Double::sum)
      .flatMap(creditDebt -> productService.getProductById(productId)
        .map(ProductDto::getBalance)
        .map(balance -> balance >= Math.abs(creditDebt)));
  }
  
  @GetMapping("/{clientId}/debitCardTransactions")
  public Flux<TransactionDto> getDebitCardTransactions(@PathVariable String clientId, @RequestParam
  String debitCardId) {
    return clientService.getClientById(clientId)
      .flatMapMany(clientDto -> clientDto.getDebitCards())
      .filter(debitCardDto -> debitCardDto.getId().equals(debitCardId))
      .flatMap(debitCardDto -> debitCardService.getDebitCardTransactions(debitCardDto.getId()));
  }
  
  private ClientSummaryDto fromClientAndProductsAndDebitCardsToClientSummaryDto(
    Tuple3<ClientDto, ProductDto, DebitCardDto> tuple) {
    ClientDto clientDto = tuple.getT1();
    ProductDto productDto = tuple.getT2();
    DebitCardDto debitCardDto = tuple.getT3();
    
    ClientSummaryDto clientSummaryDto = new ClientSummaryDto();
    clientSummaryDto.setClient(clientDto);
    
    List<ProductDto> products = new ArrayList<>();
    products.add(productDto);
    clientSummaryDto.setProducts(products);
    
    List<DebitCardDto> debitCards = new ArrayList<>();
    debitCards.add(debitCardDto);
    clientSummaryDto.setDebitCards(debitCards);
    
    return clientSummaryDto;
  }

  
}

