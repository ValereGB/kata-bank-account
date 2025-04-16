package com.kata.bank.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Compte bancaire client et toutes les infos liés au compte 
 */
public class Account {
    private final String accountNumber;    // Numéro de compte
    private final String owner;            // Propriétaire du compte
    private BigDecimal balance;            // Solde 
    private final LocalDateTime creationDate;  // Date de création
    private final List<Transaction> transactions;  // Historique des transactions

    /**
     * Constructeur pour créer un nouveau compte
     * @param accountNumber Numéro unique du compte
     * @param owner Nom du propriétaire
     */
    public Account(String accountNumber, String owner) {
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.balance = BigDecimal.ZERO;
        this.creationDate = LocalDateTime.now();
        this.transactions = new ArrayList<>();
    }

    /**
     * Faire un depot
     * @param amount Montant à déposer
     * @throws IllegalArgumentException Si le montant est négatif ou nul alors exception
     */
    public void deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Le montant du dépôt doit être supérieur à 0");
        }
        // On ajoute le montant au compte et on ajoute aussi la transaction à notre "historique"
        this.balance = this.balance.add(amount); 
        this.transactions.add(new Transaction(amount, TransactionType.DEPOSIT, this.accountNumber)); 
    }

    /**
     * Faire un retrait
     * @param amount Montant à retirer
     * @throws IllegalArgumentException Si le montant est négatif, nul ou supérieur au solde
     */
    public void withdraw(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Le montant du retrait doit être supérieur à 0");
        }
        if (amount.compareTo(this.balance) > 0) {
            throw new IllegalArgumentException("Solde insuffisant");
        }
        this.balance = this.balance.subtract(amount);
        this.transactions.add(new Transaction(amount, TransactionType.WITHDRAW, this.accountNumber));
    }

    // Getters
    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwner() {
        return owner;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public List<Transaction> getTransactions() {
        return new ArrayList<>(transactions); // Retourne une copie par sécurité ( pas de modif' sur la liste )
    }
} 