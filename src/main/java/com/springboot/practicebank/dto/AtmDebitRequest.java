package com.springboot.practicebank.dto;

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
    private String userAccountNumber;

    @NotBlank
    @Size(min = 4, max = 4, message = "Wrong Pin Number: Pin number must be a 4 digit number")
    private String userPinNumber;

    private BigDecimal amount;
}
