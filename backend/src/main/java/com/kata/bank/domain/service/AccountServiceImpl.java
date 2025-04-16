package com.kata.bank.domain.service;

import com.kata.bank.domain.exception.*;
import com.kata.bank.domain.model.Account;
import com.kata.bank.domain.model.Transaction;
import com.kata.bank.domain.port.api.AccountService;
import com.kata.bank.domain.port.spi.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Service de gestion des comptes
 */
public class AccountServiceImpl implements AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
    private final AccountRepository accountRepository;

    /**
     * Constructeur 
     * @param accountRepository 
     */
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    

    @Override
    public void createAccount(String accountNumber, String owner) {
        // Vérifie si le compte existe 
        if (accountRepository.existsByAccountNumber(accountNumber)) {
            throw new AccountAlreadyExistsException("Un compte avec le numéro " + accountNumber + " existe déjà");
        }
        Account account = new Account(accountNumber, owner);
        accountRepository.save(account);
    }

    @Override
    public Optional<Account> findByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }

    @Override
    public void deposit(String accountNumber, BigDecimal amount) {
        validateAmount(amount);
        
        Account account = findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Le compte " + accountNumber + " n'existe pas"));
        
        // Effectue le dépôt
        account.deposit(amount);
        
        // Sauvegarde les modifications
        accountRepository.save(account);
    }

    @Override
    public void withdraw(String accountNumber, BigDecimal amount) {
        validateAmount(amount);
        
        Account account = findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Le compte " + accountNumber + " n'existe pas"));
        
        if (account.getBalance().compareTo(amount) < 0) {
            String message = "Solde insuffisant. Solde actuel: " + account.getBalance() + "€, " +
                           "Montant du retrait: " + amount + "€";
            logger.warn(message);
            throw new InsufficientFundsException(message);
        }
        
        // Effectue le retrait
        account.withdraw(amount);
        
        // Sauvegarde les modifications
        accountRepository.save(account);
    }

    private void validateAmount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException("Le montant ne peut pas être négatif");
        }
    }

    @Override
    public BigDecimal getBalance(String accountNumber) {
        return findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Le compte " + accountNumber + " n'existe pas"))
                .getBalance();
    }

    @Override
    public List<Transaction> getTransactions(String accountNumber) {
        return findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Le compte " + accountNumber + " n'existe pas"))
                .getTransactions();
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public void deleteAccount(String accountNumber) {
        if (!accountRepository.existsByAccountNumber(accountNumber)) {
            throw new AccountNotFoundException("Le compte " + accountNumber + " n'existe pas");
        }
        accountRepository.deleteByAccountNumber(accountNumber);
    }
} 