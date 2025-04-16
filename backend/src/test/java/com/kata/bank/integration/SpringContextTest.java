package com.kata.bank.integration;

import com.kata.bank.domain.port.api.AccountService;
import com.kata.bank.domain.port.spi.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class SpringContextTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void contextLoads() {
        // Vérifie que le contexte charge
        assertNotNull(accountService, "AccountService ne devrait pas être null");
        assertNotNull(accountRepository, "AccountRepository ne devrait pas être null");
    }

    @Test
    void beansAreProperlyInjected() {
        // Vérifie que les beans sont ok
        assertNotNull(accountService, "AccountService devrait être injecté");
        assertNotNull(accountRepository, "AccountRepository devrait être injecté");
    }
} 