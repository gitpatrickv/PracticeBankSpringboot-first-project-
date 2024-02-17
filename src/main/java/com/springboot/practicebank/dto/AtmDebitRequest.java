package com.springboot.practicebank.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank
    @NotNull
    @Size(min = 11, max = 11, message = "Invalid Account Number")
    private String userAccountNumber;

    @NotBlank
    @NotNull
    @Size(min = 4, max = 4, message = "Pin Number must be a 4 digit number")
    private String userPinNumber;

    private BigDecimal amount;
}
