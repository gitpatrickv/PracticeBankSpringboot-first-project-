package com.springboot.practicebank.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TransactionModel {

    private String transactionType;
    private BigDecimal amount;
    private String accountNumber;
    private String status;
}
