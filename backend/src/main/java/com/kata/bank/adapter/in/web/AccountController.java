package com.kata.bank.adapter.in.web;

import com.kata.bank.domain.model.Account;
import com.kata.bank.domain.model.Transaction;
import com.kata.bank.domain.port.api.AccountService;
import com.kata.bank.domain.exception.AccountNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<Void> createAccount(
            @RequestParam String accountNumber,
            @RequestParam String owner) {
        accountService.createAccount(accountNumber, owner);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<Account> getAccount(@PathVariable String accountNumber) {
        Account account = accountService.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Le compte " + accountNumber + " n'existe pas"));
        return ResponseEntity.ok(account);
    }

    @PostMapping("/{accountNumber}/deposit")
    public ResponseEntity<Void> deposit(
            @PathVariable String accountNumber,
            @RequestParam BigDecimal amount) {
        accountService.deposit(accountNumber, amount);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{accountNumber}/withdraw")
    public ResponseEntity<Void> withdraw(
            @PathVariable String accountNumber,
            @RequestParam BigDecimal amount) {
        accountService.withdraw(accountNumber, amount);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{accountNumber}/balance")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable String accountNumber) {
        BigDecimal balance = accountService.getBalance(accountNumber);
        return ResponseEntity.ok(balance);
    }

    @GetMapping("/{accountNumber}/transactions")
    public ResponseEntity<List<TransactionResponse>> getTransactions(@PathVariable String accountNumber) {
        var transactions = accountService.getTransactions(accountNumber);
        var response = transactions.stream()
                .map(t -> new TransactionResponse(
                        t.getDate(),
                        t.getType().name(),
                        t.getAmount()))
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }
    @DeleteMapping("/{accountNumber}")
    public ResponseEntity<Void> deleteAccount(@PathVariable String accountNumber) {
        try {
            accountService.deleteAccount(accountNumber);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
} 