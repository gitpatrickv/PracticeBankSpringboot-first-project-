package com.springboot.practicebank.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank
    @NotNull
    @Size(min = 11, max = 11, message = "Invalid Account Number")
    private String accountNumber;

    @NotNull
    @NotBlank
    private String currentPassword;

    @NotNull
    @NotBlank
    @Size(min = 8, max = 20, message = "Password length must be between 8 and 20 characters")
    private String newPassword;

    @NotNull
    @NotBlank
    @Size(min = 8, max = 20, message = "Password length must be between 8 and 20 characters")
    private String confirmationPassword;
}
