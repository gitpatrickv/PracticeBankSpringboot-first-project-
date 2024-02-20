package com.springboot.practicebank.dto;

import com.springboot.practicebank.validation.AccountNumberValid;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangePasswordRequest {
    @Valid

    @AccountNumberValid
    private String accountNumber;


    @NotBlank
    private String currentPassword;


    @NotBlank
    @Size(min = 8, max = 20, message = "{password.invalid}")
    private String newPassword;


    @NotBlank
    @Size(min = 8, max = 20, message = "{password.invalid}")
    private String confirmationPassword;
}
