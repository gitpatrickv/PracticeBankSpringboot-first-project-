package com.springboot.practicebank.dto;

import com.springboot.practicebank.validation.AccountNumberValid;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreditRequest {
    @Valid

    @AccountNumberValid
    private String accountNumber;

    private BigDecimal amount;
}
