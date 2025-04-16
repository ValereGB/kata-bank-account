package com.kata.bank.adapter.out.persistence;

import com.kata.bank.domain.model.Account;
import com.kata.bank.domain.port.spi.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AccountRepositoryImpl implements AccountRepository {
    private static final Logger logger = LoggerFactory.getLogger(AccountRepositoryImpl.class);
    private Map<String, Account> accounts = new HashMap<>();

    @Override
    public Account save(Account account) {
        logger.info("Saving account: {}", account.getAccountNumber());
        accounts.put(account.getAccountNumber(), account);
        return account;
    }

    @Override
    public Optional<Account> findByAccountNumber(String accountNumber) {
        return Optional.ofNullable(accounts.get(accountNumber));
    }

    @Override
    public boolean existsByAccountNumber(String accountNumber) {
        return accounts.containsKey(accountNumber);
    }

    @Override
    public List<Account> findAll() {
        return new ArrayList<>(accounts.values());
    }

    @Override
    public void deleteAll() {
        logger.info("Deleting all accounts");
        accounts.clear();
    }

    @Override
    public void deleteByAccountNumber(String accountNumber) {
        logger.info("Deleting account: {}", accountNumber);
        accounts.remove(accountNumber);
        logger.info("Accounts after deletion: {}", accounts.keySet());
    }
} 