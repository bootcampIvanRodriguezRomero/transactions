package com.bootcamp.transactions.application.services;


import com.bootcamp.transactions.infrastructure.rest.dto.ClientDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientServiceImpl implements ClientService {
  
  private final WebClient.Builder webClientBuilder;
  private final String clientServiceUrl = "http://client/clients";
  
  @Override
  public Flux<ClientDto> getAllClients() {
    return webClientBuilder.build()
      .get()
      .uri(clientServiceUrl)
      .retrieve()
      .bodyToFlux(ClientDto.class);
  }
  
  @Override
  public Mono<ClientDto> getClientById(String id) {
    return webClientBuilder.build()
      .get()
      .uri(clientServiceUrl + "/{id}", id)
      .retrieve()
      .bodyToMono(ClientDto.class);
  }
  
  @Override
  public Mono<ClientDto> createClient(ClientDto clientDto) {
    return webClientBuilder.build()
      .post()
      .uri(clientServiceUrl)
      .bodyValue(clientDto)
      .retrieve()
      .bodyToMono(ClientDto.class);
  }
  
  @Override
  public Mono<ClientDto> modifyClient(String id, ClientDto clientDto) {
    return webClientBuilder.build()
      .put()
      .uri(clientServiceUrl + "/{id}", id)
      .bodyValue(clientDto)
      .retrieve()
      .bodyToMono(ClientDto.class);
  }
  
  @Override
  public Mono<Void> deleteClient(String id) {
    return webClientBuilder.build()
      .delete()
      .uri(clientServiceUrl + "/{id}", id)
      .retrieve()
      .bodyToMono(Void.class);
  }
  
  @Override
  public Mono<Void> deleteAllClients() {
    return webClientBuilder.build()
      .delete()
      .uri(clientServiceUrl)
      .retrieve()
      .bodyToMono(Void.class);
  }
}
