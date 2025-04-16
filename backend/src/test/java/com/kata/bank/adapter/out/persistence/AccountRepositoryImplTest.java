package com.kata.bank.adapter.out.persistence;

import com.kata.bank.domain.model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AccountRepositoryImplTest {

    private AccountRepositoryImpl repository;
    private static final String ACCOUNT_NUMBER = "FR123456789";
    private static final String OWNER = "Jean Dupont";

    @BeforeEach
    void setUp() {
        repository = new AccountRepositoryImpl();
    }

    @Test
    void saveAndFindAccount() {
        // Given
        Account account = new Account(ACCOUNT_NUMBER, OWNER);

        // When
        Account savedAccount = repository.save(account);
        Optional<Account> foundAccount = repository.findByAccountNumber(ACCOUNT_NUMBER);

        // Then
        assertTrue(foundAccount.isPresent());
        assertEquals(ACCOUNT_NUMBER, foundAccount.get().getAccountNumber());
        assertEquals(OWNER, foundAccount.get().getOwner());
    }

    @Test
    void accountNotFound() {
        // When
        Optional<Account> foundAccount = repository.findByAccountNumber("UNKNOWN");

        // Then
        assertFalse(foundAccount.isPresent());
    }

    @Test
    void accountExist() {
        // Given
        Account account = new Account(ACCOUNT_NUMBER, OWNER);
        repository.save(account);

        // When & Then
        assertTrue(repository.existsByAccountNumber(ACCOUNT_NUMBER));
        assertFalse(repository.existsByAccountNumber("UNKNOWN"));
    }

    @Test
    void updateExistingAccount() {
        // Given
        Account account = new Account(ACCOUNT_NUMBER, OWNER);
        repository.save(account);
        
        // When
        account.deposit(new BigDecimal("100.00"));
        Account updatedAccount = repository.save(account);
        
        // Then
        Optional<Account> foundAccount = repository.findByAccountNumber(ACCOUNT_NUMBER);
        assertTrue(foundAccount.isPresent());
        assertEquals(new BigDecimal("100.00"), foundAccount.get().getBalance());
    }
} 