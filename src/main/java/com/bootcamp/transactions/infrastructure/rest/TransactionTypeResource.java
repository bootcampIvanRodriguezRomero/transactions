package com.bootcamp.transactions.infrastructure.rest;

import com.bootcamp.transactions.infrastructure.repository.TransactionTypeRepository;
import com.bootcamp.transactions.infrastructure.repository.dao.TransactionTypeDao;
import com.bootcamp.transactions.infrastructure.rest.dto.TransactionTypeDto;
import com.bootcamp.transactions.infrastructure.rest.dto.TransactionTypePostDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/transactionType")
@RequiredArgsConstructor
@Slf4j
public class TransactionTypeResource {
  private final TransactionTypeRepository transactionTypeRepository;
  
  @GetMapping
  public Flux<TransactionTypeDto> getAllTransactionTypes() {
    return transactionTypeRepository.findAll()
      .map(this::fromTransactionTypeDaoToTransactionTypeDto);
  }
  
  @GetMapping("/{id}")
  public Mono<TransactionTypeDto> findTransactionTypeById(@PathVariable String id) {
    return transactionTypeRepository.findById(id)
      .map(this::fromTransactionTypeDaoToTransactionTypeDto);
  }
  
  @PostMapping
  public Mono<TransactionTypeDto> createTransactionType(
    @Valid @RequestBody TransactionTypePostDto transactionTypePostDto) {
    TransactionTypeDao transactionTypeDao =
      fromTransactionTypePostDtoToTransactionTypeDao(transactionTypePostDto);
    return transactionTypeRepository.save(transactionTypeDao)
      .map(this::fromTransactionTypeDaoToTransactionTypeDto);
  }
  
  @PutMapping("/{id}")
  public Mono<TransactionTypeDto> updateTransactionType(@PathVariable String id, @Valid @RequestBody
  TransactionTypeDto transactionTypeDto) {
    return transactionTypeRepository.findById(id)
      .flatMap(existingTransactionType -> {
        existingTransactionType.setName(transactionTypeDto.getName());
        return transactionTypeRepository.save(existingTransactionType);
      })
      .map(this::fromTransactionTypeDaoToTransactionTypeDto);
  }
  
  @DeleteMapping("/{id}")
  public Mono<Void> deleteTransactionType(@PathVariable String id) {
    log.info("Deleting transaction type with ID: {}", id);
    return transactionTypeRepository.deleteById(id);
  }
  
  private TransactionTypeDto fromTransactionTypeDaoToTransactionTypeDto(
    TransactionTypeDao transactionTypeDao) {
    TransactionTypeDto transactionTypeDto = new TransactionTypeDto();
    transactionTypeDto.setId(transactionTypeDao.getId());
    transactionTypeDto.setName(transactionTypeDao.getName());
    return transactionTypeDto;
  }
  
  private TransactionTypeDao fromTransactionTypePostDtoToTransactionTypeDao(
    TransactionTypePostDto transactionTypePostDto) {
    TransactionTypeDao transactionTypeDao = new TransactionTypeDao();
    transactionTypeDao.setId(transactionTypePostDto.getId());
    transactionTypeDao.setName(transactionTypePostDto.getName());
    return transactionTypeDao;
  }
}
