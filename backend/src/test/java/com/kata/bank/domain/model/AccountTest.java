package com.kata.bank.domain.model;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests liés au compte
 */
public class AccountTest {

    @Test
    public void testCreateAccount() {
        // Création de compte
        Account account = new Account("FR123456789", "Jean Roger");
        
        // Vérifications
        assertEquals("FR123456789", account.getAccountNumber());
        assertEquals("Jean Roger", account.getOwner());
        assertEquals(BigDecimal.ZERO, account.getBalance());
        assertTrue(account.getTransactions().isEmpty());
    }

    @Test
    public void testDeposit() {
        // Création de compte
        Account account = new Account("FR123456789", "John Doe");
        
        // Dépot d'argent
        account.deposit(new BigDecimal("100.00"));
        
        // Vérifications
        assertEquals(new BigDecimal("100.00"), account.getBalance());
        assertEquals(1, account.getTransactions().size());
        
        // Vérification de la transaction
        Transaction transaction = account.getTransactions().get(0);
        assertEquals(new BigDecimal("100.00"), transaction.getAmount());
        assertEquals(TransactionType.DEPOSIT, transaction.getType());
    }

    @Test
    public void testWithdraw() {
        // Création de compte avec de l'argent
        Account account = new Account("FR123456789", "John Doe");
        account.deposit(new BigDecimal("200.00"));
        
        // Retrait d'argent
        account.withdraw(new BigDecimal("50.00"));
        
        // Vérifications
        assertEquals(new BigDecimal("150.00"), account.getBalance());
        assertEquals(2, account.getTransactions().size());
        
        // Vérification d'une transaction
        Transaction transaction = account.getTransactions().get(1);
        assertEquals(new BigDecimal("50.00"), transaction.getAmount());
        assertEquals(TransactionType.WITHDRAW, transaction.getType());
    }

    @Test
    public void testDepositNegativeAmount() {
        // Création de compte
        Account account = new Account("FR123456789", "John Doe");
        
        // Tentative de dépot avec un montant négatif
        assertThrows(IllegalArgumentException.class, () -> {
            account.deposit(new BigDecimal("-100.00"));
        });
    }

    @Test
    public void testWithdrawInsufficientBalance() {
        // Création de compte 
        Account account = new Account("FR123456789", "John Doe");
        account.deposit(new BigDecimal("50.00"));
        
        // Tentative de retrait trop élevé
        assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(new BigDecimal("100.00"));
        });
    }
} 