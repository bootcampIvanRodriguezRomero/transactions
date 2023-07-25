package com.bootcamp.transactions.infrastructure.rest;

import com.bootcamp.transactions.infrastructure.repository.AccountRepository;
import com.bootcamp.transactions.infrastructure.repository.TransactionsRepository;
import com.bootcamp.transactions.infrastructure.repository.dao.AccountDao;
import com.bootcamp.transactions.infrastructure.repository.dao.TransactionDao;
import com.bootcamp.transactions.infrastructure.rest.dto.TransactionDto;
import jakarta.validation.Valid;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/banking")
@RequiredArgsConstructor
@Slf4j
public class BankingController {
  private final TransactionsRepository transactionsRepository;
  private final AccountRepository accountsRepository;
  
  private static final int MAX_FREE_TRANSACTIONS = 20;
  private static final double COMMISSION_RATE = 0.05; // 5% commission rate
  
  @GetMapping("/accounts/{accountId}/transaction")
  public Mono<TransactionDto> getAccountTransaction(@PathVariable String accountId) {
    return transactionsRepository.findById(accountId)
      .map(this::fromTransactionDaoToTransactionDto);
  }
  
  @PostMapping("/accounts/{accountId}/transfer/{destinationAccountId}")
  public Mono<TransactionDto> transferFunds(@PathVariable String accountId,
                                            @PathVariable String destinationAccountId, @Valid
                                            @RequestBody TransactionDto transactionDto) {
    return accountsRepository.findById(accountId)
      .zipWith(accountsRepository.findById(destinationAccountId))
      .flatMap(tuple -> {
        AccountDao sourceAccount = tuple.getT1();
        AccountDao destinationAccount = tuple.getT2();
        
        if (sourceAccount.getBankId().equals(destinationAccount.getBankId())) {
          return performIntraBankTransfer(sourceAccount, destinationAccount,
            transactionDto.getAmount());
        } else {
          return Mono.error(new IllegalArgumentException("Interbank transfers are not supported."));
        }
      });
  }
  
  private Mono<TransactionDto> performIntraBankTransfer(AccountDao sourceAccount,
                                                        AccountDao destinationAccount,
                                                        double amount) {
    if (sourceAccount.getBalance() >= amount) {
      sourceAccount.setBalance(sourceAccount.getBalance() - amount);
      destinationAccount.setBalance(destinationAccount.getBalance() + amount);
      
      Mono<AccountDao> sourceAccountUpdate = accountsRepository.save(sourceAccount);
      Mono<AccountDao> destinationAccountUpdate = accountsRepository.save(destinationAccount);
      
      return Mono.zip(sourceAccountUpdate, destinationAccountUpdate)
        .flatMap(tuple -> {
          TransactionDao transactionDao = new TransactionDao();
          transactionDao.setAmount(amount);
          return transactionsRepository.save(transactionDao);
        })
        .map(this::fromTransactionDaoToTransactionDto);
    } else {
      return Mono.error(new IllegalArgumentException("Insufficient funds in the source account."));
    }
  }
  
  private TransactionDto fromTransactionDaoToTransactionDto(TransactionDao transactionDao) {
    TransactionDto transactionDto = new TransactionDto();
    transactionDto.setId(transactionDao.getId());
    transactionDto.setDate(transactionDao.getDate());
    transactionDto.setAmount(transactionDao.getAmount());
    return transactionDto;
  }
}
