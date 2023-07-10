package com.bootcamp.transactions.infrastructure.rest;

import com.bootcamp.transactions.infrastructure.repository.BalanceRepository;
import com.bootcamp.transactions.infrastructure.repository.ClientRepository;
import com.bootcamp.transactions.infrastructure.repository.ProductRepository;
import com.bootcamp.transactions.infrastructure.repository.dao.BalanceDao;
import com.bootcamp.transactions.infrastructure.rest.dto.AmountDto;
import com.bootcamp.transactions.infrastructure.rest.dto.BalanceDto;
import com.bootcamp.transactions.infrastructure.rest.dto.BalancePostDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/balance")
@RequiredArgsConstructor
@Slf4j
public class BalanceResource {
    private final BalanceRepository balanceRepository;
    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;
    @GetMapping
    public Flux<BalanceDto> getAllBalances() {
        return balanceRepository.findAll()
                .map(this::fromBalanceDaoToBalanceDto);
    }

    @GetMapping("/{id}")
    public Mono<BalanceDto> findBalanceById(@PathVariable String id) {
        return balanceRepository.findById(id)
                .map(this::fromBalanceDaoToBalanceDto);
    }

    @PostMapping
    public Mono<BalanceDto> createBalance(@Valid @RequestBody BalancePostDto balancePostDto) {
        BalanceDao balanceDao = fromBalancePostDtoToBalanceDao(balancePostDto);
        return balanceRepository.save(balanceDao)
                .map(this::fromBalanceDaoToBalanceDto);
    }

    @PutMapping("/{id}")
    public Mono<BalanceDto> updateBalance(@PathVariable String id, @Valid @RequestBody BalanceDto balanceDto) {
        return balanceRepository.findById(id)
                .flatMap(existingBalance -> {
                    existingBalance.setProductId(balanceDto.getProductId());
                    existingBalance.setBalance(balanceDto.getBalance());
                    return balanceRepository.save(existingBalance);
                })
                .map(this::fromBalanceDaoToBalanceDto);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteBalance(@PathVariable String id) {
        log.info("Deleting balance with ID: {}", id);
        return balanceRepository.deleteById(id);
    }
    @PostMapping("/{id}/deposit")
    public Mono<BalanceDto> depositToBalance(@PathVariable String id, @Valid @RequestBody AmountDto amountDto) {
        return balanceRepository.findById(id)
                .flatMap(existingBalance -> {
                    double currentBalance = existingBalance.getBalance();
                    double depositAmount = amountDto.getAmount();
                    double newBalance = currentBalance + depositAmount;
                    existingBalance.setBalance(newBalance);
                    return balanceRepository.save(existingBalance);
                })
                .map(this::fromBalanceDaoToBalanceDto);
    }

    @PostMapping("/{id}/withdraw")
    public Mono<BalanceDto> withdrawFromBalance(@PathVariable String id, @Valid @RequestBody AmountDto amountDto) {
        return balanceRepository.findById(id)
                .flatMap(existingBalance -> {
                    double currentBalance = existingBalance.getBalance();
                    double withdrawAmount = amountDto.getAmount();
                    if (withdrawAmount > currentBalance) {
                        Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient balance"));
                    }
                    double newBalance = currentBalance - withdrawAmount;
                    existingBalance.setBalance(newBalance);
                    return balanceRepository.save(existingBalance);
                })
                .map(this::fromBalanceDaoToBalanceDto);
    }
    @GetMapping("/products/{productId}/clients/{clientId}/balance")
    public Mono<BalanceDto> getProductClientBalance(@PathVariable String productId, @PathVariable String clientId) {
        return balanceRepository.findByProductIdAndClientId(productId, clientId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Balance not found")))
                .map(this::fromBalanceDaoToBalanceDto);
    }

    @GetMapping("/clients/{clientId}/products/{productId}/balance")
    public Mono<BalanceDto> getClientProductBalance(@PathVariable String clientId, @PathVariable String productId) {
        return balanceRepository.findByProductIdAndClientId(productId, clientId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Balance not found")))
                .map(this::fromBalanceDaoToBalanceDto);
    }
    private BalanceDto fromBalanceDaoToBalanceDto(BalanceDao balanceDao) {
        BalanceDto balanceDto = new BalanceDto();
        balanceDto.setId(balanceDao.getId());
        balanceDto.setProductId(balanceDao.getProductId());
        balanceDto.setBalance(balanceDao.getBalance());
        return balanceDto;
    }

    private BalanceDao fromBalancePostDtoToBalanceDao(BalancePostDto balancePostDto) {
        BalanceDao balanceDao = new BalanceDao();
        balanceDao.setId(balancePostDto.getId());
        balanceDao.setProductId(balancePostDto.getProductId());
        balanceDao.setBalance(balancePostDto.getBalance());
        return balanceDao;
    }
}

