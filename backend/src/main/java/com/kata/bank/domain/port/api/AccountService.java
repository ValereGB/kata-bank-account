package com.kata.bank.domain.port.api;

import com.kata.bank.domain.model.Account;
import com.kata.bank.domain.model.Transaction;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Interface du compte bancaire
 */
public interface AccountService {
    
    /**
     * Crée un compte bancaire
     * @param accountNumber Numéro du compte
     * @param owner Nom du propriétaire
     */
    void createAccount(String accountNumber, String owner);

    /**
     * Recherche un compte par numéro
     * @param accountNumber Numéro du compte à rechercher
     * @return Le compte trouvé
     */
    Optional<Account> findByAccountNumber(String accountNumber);

    /**
     * Effectue un dépot
     * @param accountNumber Numéro du compte
     * @param amount Montant à déposer
     * @throws IllegalArgumentException Exception si le montant est négatif ou nul
     */
    void deposit(String accountNumber, BigDecimal amount);

    /**
     * Effectue un retrait
     * @param accountNumber Numéro du compte
     * @param amount Montant à retirer
     * @throws IllegalArgumentException Exception si le montant est négatif, nul ou supérieur au solde de base
     */
    void withdraw(String accountNumber, BigDecimal amount);

    /**
     * Avoir le solde d'un compte
     * @param accountNumber Numéro du compte
     * @return Le solde actuel
     */
    BigDecimal getBalance(String accountNumber);

    /**
     * Historique des transactions d'un compte
     * @param accountNumber Numéro du compte
     * @return La liste des transactions
     */
    List<Transaction> getTransactions(String accountNumber);
    
    /**
     * Listing des comptes existant
     * @return La liste des compte
     */
    List<Account> getAllAccounts();

    /**
     * Supprime un compte
     * @param accountNumber Numéro du compte à supprimer
     */
    void deleteAccount(String accountNumber);
} 

