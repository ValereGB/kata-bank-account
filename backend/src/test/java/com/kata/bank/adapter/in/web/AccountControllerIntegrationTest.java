package com.kata.bank.adapter.in.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class AccountControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    void testCompte() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        
        // Création du compte
        mockMvc.perform(post("/api/accounts")
                .param("accountNumber", "FR123456789")
                .param("owner", "Jean Dupont"))
                .andExpect(status().isOk());

        // Dépôt
        mockMvc.perform(post("/api/accounts/FR123456789/deposit")
                .param("amount", "1000"))
                .andExpect(status().isOk());

        // Vérification du solde
        mockMvc.perform(get("/api/accounts/FR123456789/balance"))
                .andExpect(status().isOk())
                .andExpect(content().string("1000"));

        // Retrait
        mockMvc.perform(post("/api/accounts/FR123456789/withdraw")
                .param("amount", "500"))
                .andExpect(status().isOk());

        // Vérification du solde final
        mockMvc.perform(get("/api/accounts/FR123456789/balance"))
                .andExpect(status().isOk())
                .andExpect(content().string("500"));
    }

    @Test
    void testErreurs() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        // Création du compte pour les tests
        mockMvc.perform(post("/api/accounts")
                .param("accountNumber", "FR987654321")
                .param("owner", "Jean Dupont"))
                .andExpect(status().isOk());

        // Test dépôt montant négatif
        mockMvc.perform(post("/api/accounts/FR987654321/deposit")
                .param("amount", "-100"))
                .andExpect(status().isBadRequest());

        // Test retrait montant négatif
        mockMvc.perform(post("/api/accounts/FR987654321/withdraw")
                .param("amount", "-100"))
                .andExpect(status().isBadRequest());

        // Test compte inexistant
        mockMvc.perform(get("/api/accounts/INCONNU/balance"))
                .andExpect(status().isNotFound());

        // Test retrait avec solde insuffisant
        mockMvc.perform(post("/api/accounts/FR987654321/withdraw")
                .param("amount", "1000000"))
                .andExpect(status().isBadRequest());
    }
} 