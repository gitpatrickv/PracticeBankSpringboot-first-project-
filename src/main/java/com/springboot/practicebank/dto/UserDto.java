package com.springboot.practicebank.dto;

import com.springboot.practicebank.entity.Role;
import com.springboot.practicebank.entity.Status;
import com.springboot.practicebank.validation.AgeValid;
import com.springboot.practicebank.validation.GenderValid;
import com.springboot.practicebank.validation.UniqueEmailValid;
import com.springboot.practicebank.validation.marker.OnUpdate;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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

public class UserDto {
    @Valid

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @GenderValid
    private String gender;

    @NotBlank
    private String address;

    @NotBlank
    private String phoneNumber;

    @AgeValid
    private Integer age;

    @Email
    @UniqueEmailValid(groups = OnUpdate.class)
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 8, max = 20, message = "{password.invalid}")
    private String password;

    private String accountNumber;

    @NotBlank
    @Size(min = 4, max = 4, message = "{pin.number.invalid}")
    @Pattern(regexp = "\\d{4}", message = "{pin.number.size}")
    private String atmPin;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Role role;

    private BigDecimal accountBalance;


}
