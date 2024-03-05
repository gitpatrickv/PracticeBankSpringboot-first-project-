package com.springboot.practicebank.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountInfo {

    private String accountNumber;
    private BigDecimal amount;
    private BigDecimal accountBalance;


}
