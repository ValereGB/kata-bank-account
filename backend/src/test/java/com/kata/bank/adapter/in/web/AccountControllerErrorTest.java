package com.kata.bank.adapter.in.web;

import com.kata.bank.domain.port.spi.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
class AccountControllerErrorTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private AccountRepository accountRepository;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        accountRepository.deleteAll();
    }

    @Test
    void testWithdrawOnNonExistentAccount() throws Exception {
        mockMvc.perform(post("/api/accounts/NONEXISTENT/withdraw")
                .param("amount", "100.00"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Compte non trouvé !"));
    }

    @Test
    void testWithdrawWithInsufficientFunds() throws Exception {
        // Création du compte
        mockMvc.perform(post("/api/accounts")
                .param("accountNumber", "FR123456789")
                .param("owner", "Jean Dupont"))
                .andExpect(status().isOk());

        // Tentative de retrait avec solde insuffisant
        mockMvc.perform(post("/api/accounts/FR123456789/withdraw")
                .param("amount", "500.00"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Fonds insuffisants sur ce compte !"));
    }

    @Test
    void testCreateAccountWithExistingNumber() throws Exception {
        // Premier compte
        mockMvc.perform(post("/api/accounts")
                .param("accountNumber", "FR123456789")
                .param("owner", "Jean Dupont"))
                .andExpect(status().isOk());

        // Tentative de création d'un compte avec le même numéro
        mockMvc.perform(post("/api/accounts")
                .param("accountNumber", "FR123456789")
                .param("owner", "Autre Propriétaire"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Compte existant"));
    }

    @Test
    void testDepositWithNegativeAmount() throws Exception {
        // Création du compte
        mockMvc.perform(post("/api/accounts")
                .param("accountNumber", "FR123456789")
                .param("owner", "Jean Dupont"))
                .andExpect(status().isOk());

        // Tentative de dépôt négatif
        mockMvc.perform(post("/api/accounts/FR123456789/deposit")
                .param("amount", "-100.00"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Montant invalide"));
    }
} 