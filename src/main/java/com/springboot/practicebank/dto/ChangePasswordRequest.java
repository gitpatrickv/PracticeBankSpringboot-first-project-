package com.springboot.practicebank.dto;

import com.springboot.practicebank.validation.PasswordMatchValid;
import com.springboot.practicebank.validation.PasswordValid;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@PasswordMatchValid
public class ChangePasswordRequest {
    @Valid

    @PasswordValid
    private String oldPassword;

    private String password;

    private String confirmPassword;
}
