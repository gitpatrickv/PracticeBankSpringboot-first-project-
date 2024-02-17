package com.springboot.practicebank.dto;


import com.springboot.practicebank.entity.Role;
import com.springboot.practicebank.entity.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    @Valid

    @NotNull
    @NotBlank
    private String firstName;
    @NotNull
    @NotBlank
    private String lastName;
    @NotNull
    @NotBlank
    private String gender;
    @NotNull
    @NotBlank
    private String address;
    @NotNull
    @NotBlank
    private String phoneNumber;
    @Email
    @NotNull
    @NotBlank
    private String email;
    @Size(min = 8, max = 20, message = "Password length must be between 8 and 20 characters")
    private String password;
    private String accountNumber;
    @NotNull
    @NotBlank
    @Size(min = 4, max = 4, message = "Pin Number length must only contains 4 digit number")
    @Pattern(regexp = "\\d{4}", message = "Pin Number must be a 4 digit number")
    private String atmPin;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private Role role;

    private BigDecimal accountBalance;




}
