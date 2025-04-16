// @javac -source 21 -target 
package com.kata.bank.domain.service;

import com.kata.bank.domain.exception.AccountAlreadyExistsException;
import com.kata.bank.domain.model.Account;
import com.kata.bank.domain.model.Transaction;
import com.kata.bank.domain.port.spi.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests pour AccountServiceImpl
 */
public class AccountServiceImplTest {

    private AccountServiceImpl accountService;

    // Mock du repository
    @Mock
    private AccountRepository accountRepository;

    // Données de test
    private static final String ACCOUNT_NUMBER = "FR123456789";
    private static final String OWNER = "Jean Roger";

    @BeforeEach
    public void setUp() {
        // Initialise les mocks
        MockitoAnnotations.openMocks(this);
        
        // Crée le service avec le mock du repository
        accountService = new AccountServiceImpl(accountRepository);
    }

    // Création de compte
    @Test
    public void testCreateAccount() {
        // Configuration du mock
        when(accountRepository.existsByAccountNumber(ACCOUNT_NUMBER)).thenReturn(false);
        when(accountRepository.save(any(Account.class))).thenAnswer(i -> i.getArguments()[0]);

        // Exécution
        accountService.createAccount(ACCOUNT_NUMBER, OWNER);

        // Vérifications
        verify(accountRepository).existsByAccountNumber(ACCOUNT_NUMBER);
        verify(accountRepository).save(any(Account.class));
    }
    // Création de compte mais avec numéro existant
    @Test
    public void testCreateAccountAlreadyExists() {
        // Configuration du mock
        when(accountRepository.existsByAccountNumber(ACCOUNT_NUMBER)).thenReturn(true);

        // Exécution et vérification de l'exception
        assertThrows(AccountAlreadyExistsException.class, () -> {
            accountService.createAccount(ACCOUNT_NUMBER, OWNER);
        });

        // Vérification que le compte n'a pas été enregistrer quand meme
        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    public void testDeposit() {
        // Création d'un compte de test
        Account account = new Account(ACCOUNT_NUMBER, OWNER);
        
        // Configuration du mock
        when(accountRepository.findByAccountNumber(ACCOUNT_NUMBER))
            .thenReturn(Optional.of(account));
        when(accountRepository.save(any(Account.class)))
            .thenAnswer(i -> i.getArguments()[0]);

        // Exécution
        accountService.deposit(ACCOUNT_NUMBER, new BigDecimal("100.00"));

        // Vérifications
        verify(accountRepository).findByAccountNumber(ACCOUNT_NUMBER);
        verify(accountRepository).save(any(Account.class));
        assertEquals(new BigDecimal("100.00"), account.getBalance());
    }

    @Test
    public void testWithdraw() {
        // Création d'un compte de test avec de l'argent
        Account account = new Account(ACCOUNT_NUMBER, OWNER);
        account.deposit(new BigDecimal("200.00"));
        
        // Configuration du mock
        when(accountRepository.findByAccountNumber(ACCOUNT_NUMBER))
            .thenReturn(Optional.of(account));
        when(accountRepository.save(any(Account.class)))
            .thenAnswer(i -> i.getArguments()[0]);

        // Exécution
        accountService.withdraw(ACCOUNT_NUMBER, new BigDecimal("50.00"));

        // Vérifications
        verify(accountRepository).findByAccountNumber(ACCOUNT_NUMBER);
        verify(accountRepository).save(any(Account.class));
        assertEquals(new BigDecimal("150.00"), account.getBalance());
    }

    @Test
    public void testGetBalance() {
        // Création d'un compte de test avec de l'argent
        Account account = new Account(ACCOUNT_NUMBER, OWNER);
        account.deposit(new BigDecimal("100.00"));
        
        // Configuration du mock
        when(accountRepository.findByAccountNumber(ACCOUNT_NUMBER))
            .thenReturn(Optional.of(account));

        // Exécution
        BigDecimal balance = accountService.getBalance(ACCOUNT_NUMBER);

        // Vérifications
        assertEquals(new BigDecimal("100.00"), balance);
        verify(accountRepository).findByAccountNumber(ACCOUNT_NUMBER);
    }

    @Test
    public void testGetTransactions() {
        // Création d'un compte de test avec des transactions
        Account account = new Account(ACCOUNT_NUMBER, OWNER);
        account.deposit(new BigDecimal("100.00"));
        account.withdraw(new BigDecimal("50.00"));
        
        // Configuration du mock
        when(accountRepository.findByAccountNumber(ACCOUNT_NUMBER))
            .thenReturn(Optional.of(account));

        // Exécution
        var transactions = accountService.getTransactions(ACCOUNT_NUMBER);

        // Vérifications
        assertEquals(2, transactions.size());
        verify(accountRepository).findByAccountNumber(ACCOUNT_NUMBER);
    }
} 