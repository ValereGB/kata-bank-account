package com.kata.bank.config;

import com.kata.bank.adapter.out.persistence.AccountRepositoryImpl;
import com.kata.bank.domain.port.spi.AccountRepository;
import com.kata.bank.domain.service.AccountServiceImpl;
import com.kata.bank.domain.port.api.AccountService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BankConfig {

    @Bean
    public AccountRepository accountRepository() {
        return new AccountRepositoryImpl();
    }

    @Bean
    public AccountService accountService(AccountRepository accountRepository) {
        return new AccountServiceImpl(accountRepository);
    }
} 