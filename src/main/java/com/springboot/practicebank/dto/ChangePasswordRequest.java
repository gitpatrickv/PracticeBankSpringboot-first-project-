package com.springboot.practicebank.dto;

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

public class ChangePasswordRequest {
    @Valid

    @PasswordValid
    private String oldPassword;
    @PasswordValid
    //@NotBlank
    //@Size(min = 8, max = 20, message = "{password.invalid}")
    private String password;

    //@NotBlank
    //@Size(min = 8, max = 20, message = "{password.invalid}")
    @PasswordValid
    private String confirmPassword;
}
