package com.bootcamp.transactions.application.services;

import com.bootcamp.transactions.infrastructure.rest.dto.BalanceDto;
import com.bootcamp.transactions.infrastructure.rest.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
  
  private final WebClient.Builder webClientBuilder;
  private final String productServiceUrl = "http://product/products";
  
  @Override
  public Flux<ProductDto> getAllProducts() {
    return webClientBuilder.build()
      .get()
      .uri(productServiceUrl)
      .retrieve()
      .bodyToFlux(ProductDto.class);
  }
  
  @Override
  public Mono<ProductDto> getProductById(String id) {
    return webClientBuilder.build()
      .get()
      .uri(productServiceUrl + "/{id}", id)
      .retrieve()
      .bodyToMono(ProductDto.class);
  }
  
  @Override
  public Mono<ProductDto> createProduct(ProductDto productDto) {
    return webClientBuilder.build()
      .post()
      .uri(productServiceUrl)
      .bodyValue(productDto)
      .retrieve()
      .bodyToMono(ProductDto.class);
  }
  
  @Override
  public Mono<ProductDto> modifyProduct(String id, ProductDto productDto) {
    return webClientBuilder.build()
      .put()
      .uri(productServiceUrl + "/{id}", id)
      .bodyValue(productDto)
      .retrieve()
      .bodyToMono(ProductDto.class);
  }
  
  @Override
  public Mono<Void> deleteProduct(String id) {
    return webClientBuilder.build()
      .delete()
      .uri(productServiceUrl + "/{id}", id)
      .retrieve()
      .bodyToMono(Void.class);
  }
  
  @Override
  public Mono<Void> deleteAllProducts() {
    return webClientBuilder.build()
      .delete()
      .uri(productServiceUrl)
      .retrieve()
      .bodyToMono(Void.class);
  }
  @Override
  public Mono<BalanceDto> getBalanceByProductId(String productId) {
    return webClientBuilder.build()
      .get()
      .uri(productServiceUrl + "/products/{productId}/balance", productId)
      .retrieve()
      .bodyToMono(BalanceDto.class);
  }
}
