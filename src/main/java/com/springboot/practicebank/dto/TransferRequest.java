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
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransferRequest {
    @Valid

    @NotBlank
    @NotNull
    @Size(min = 11, max = 11, message = "Invalid Account Number")
    private String sourceAccountNumber;

    @NotBlank
    @NotNull
    @Size(min = 11, max = 11, message = "Invalid Account Number")
    private String destinationAccountNumber;

    private BigDecimal amount;

}
