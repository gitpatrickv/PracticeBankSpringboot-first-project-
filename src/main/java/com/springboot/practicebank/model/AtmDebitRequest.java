package com.springboot.practicebank.model;

import com.springboot.practicebank.validation.AccountNumberValid;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class AtmDebitRequest {

    @Valid

    @AccountNumberValid
    private String accountNumber;

    @NotBlank
    @Size(min = 4, max = 4, message = "{pin.number.invalid}")
    private String atmPin;

    private BigDecimal amount;
}
