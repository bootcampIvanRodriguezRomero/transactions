package com.bootcamp.transactions.infrastructure.rest;

import com.bootcamp.transactions.infrastructure.repository.TransactionsRepository;
import com.bootcamp.transactions.infrastructure.repository.dao.TransactionDao;
import com.bootcamp.transactions.infrastructure.rest.dto.TransactionDto;
import com.bootcamp.transactions.infrastructure.rest.dto.TransactionPostDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
@Slf4j
public class TransactionResource {
    private final TransactionsRepository transactionsRepository;

    @GetMapping
    public Flux<TransactionDto> getAllTransactions() {
        return transactionsRepository.findAll()
                .map(this::fromTransactionDaoToTransactionDto);
    }

    @GetMapping("/{id}")
    public Mono<TransactionDto> findTransactionById(@PathVariable String id) {
        return transactionsRepository.findById(id)
                .map(this::fromTransactionDaoToTransactionDto);
    }

    @PostMapping
    public Mono<TransactionDto> createTransaction(@Valid @RequestBody TransactionPostDto transactionPostDto) {
        TransactionDao transactionDao = fromTransactionPostDtoToTransactionDao(transactionPostDto);
        transactionDao.setDate(new Date());
        return transactionsRepository.save(transactionDao)
                .map(this::fromTransactionDaoToTransactionDto);
    }

    @PutMapping("/{id}")
    public Mono<TransactionDto> updateTransaction(@PathVariable String id, @Valid @RequestBody TransactionDto transactionDto) {
        return transactionsRepository.findById(id)
                .flatMap(existingTransaction -> {
                    existingTransaction.setAmount(transactionDto.getAmount());
                    return transactionsRepository.save(existingTransaction);
                })
                .map(this::fromTransactionDaoToTransactionDto);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteTransaction(@PathVariable String id) {
        log.info("Deleting transaction with ID: {}", id);
        return transactionsRepository.deleteById(id);
    }

    private TransactionDto fromTransactionDaoToTransactionDto(TransactionDao transactionDao) {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setId(transactionDao.getId());
        transactionDto.setDate(transactionDao.getDate());
        transactionDto.setAmount(transactionDao.getAmount());
        return transactionDto;
    }

    private TransactionDao fromTransactionPostDtoToTransactionDao(TransactionPostDto transactionPostDto) {
        TransactionDao transactionDao = new TransactionDao();
        transactionDao.setId(transactionPostDto.getId());
        transactionDao.setAmount(transactionPostDto.getAmount());
        return transactionDao;
    }
}

