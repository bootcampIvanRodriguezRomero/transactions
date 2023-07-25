package com.bootcamp.transactions.application.services;

import com.bootcamp.transactions.infrastructure.rest.dto.ClientDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClientService {
  Flux<ClientDto> getAllClients();
  
  Mono<ClientDto> getClientById(String id);
  
  Mono<ClientDto> createClient(ClientDto clientDto);
  
  Mono<ClientDto> modifyClient(String id, ClientDto clientDto);
  
  Mono<Void> deleteClient(String id);
  
  Mono<Void> deleteAllClients();
}

