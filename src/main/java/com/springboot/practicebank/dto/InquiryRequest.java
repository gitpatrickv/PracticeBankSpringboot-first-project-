package com.springboot.practicebank.dto;

import com.springboot.practicebank.validation.AccountNumberValid;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InquiryRequest {
    @Valid

    @AccountNumberValid
    private String accountNumber;
}
