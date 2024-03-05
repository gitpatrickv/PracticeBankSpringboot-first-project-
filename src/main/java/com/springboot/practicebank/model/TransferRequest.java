package com.springboot.practicebank.model;

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
public class TransferRequest {
    @Valid

    @AccountNumberValid
    private String sourceAccountNumber;

    @AccountNumberValid
    private String destinationAccountNumber;

    private BigDecimal amount;

}
