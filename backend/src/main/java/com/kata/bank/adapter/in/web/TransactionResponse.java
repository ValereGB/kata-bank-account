package com.kata.bank.adapter.in.web;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponse(
    LocalDateTime date,
    String type,
    BigDecimal amount
) {} 