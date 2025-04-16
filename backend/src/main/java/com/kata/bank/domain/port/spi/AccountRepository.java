package com.kata.bank.domain.port.spi;

import com.kata.bank.domain.model.Account;
import java.util.List;
import java.util.Optional;

/**
 * Interface de stockage et récupération des comptes bancaires
 */
public interface AccountRepository {
    
    /**
     * Sauvegarde un compte bancaire
     * @param account Le compte à sauvegarder
     * @return Le compte sauvegardé
     */
    Account save(Account account);

    /**
     * Recherche un compte par numéro
     * @param accountNumber Numéro du compte à rechercher
     * @return Le compte trouvé
     */
    Optional<Account> findByAccountNumber(String accountNumber);

    /**
     * Vérifie si un compte existe
     * @param accountNumber Numéro du compte à vérifier
     * @return true si le compte existe false sinon
     */
    boolean existsByAccountNumber(String accountNumber);

    /**
     * Listing des comptes existant
     * @return La liste des compte
     */
    List<Account> findAll();

    /**
     * Supprime tous les comptes bancaires
     */
    void deleteAll();

    /**
     * Supprime un compte par son numéro
     * @param accountNumber Numéro du compte à supprimer
     */
    void deleteByAccountNumber(String accountNumber);
} 