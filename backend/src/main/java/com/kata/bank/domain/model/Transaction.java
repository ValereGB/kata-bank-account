package com.kata.bank.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
* Transaction bancaire et type de transaction
 */
public class Transaction {
    private final BigDecimal amount;           // Le montant
    private final TransactionType type;        // Le type de transaction
    private final LocalDateTime date;          // la date
    private final String accountNumber;        // Le numero du compte où on effectue la transaction

    /**
     * Nouvelle transaction
     * @param amount Montant de la transaction
     * @param type Type de transaction
     * @param accountNumber Numéro du compte concerné
     */
    public Transaction(BigDecimal amount, TransactionType type, String accountNumber) {
        this.amount = amount;
        this.type = type;
        this.date = LocalDateTime.now();
        this.accountNumber = accountNumber;
    }

    // Getters
    public BigDecimal getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
} 