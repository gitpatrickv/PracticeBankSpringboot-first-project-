package com.springboot.practicebank.dto;

import com.springboot.practicebank.entity.Role;
import com.springboot.practicebank.entity.Status;
import com.springboot.practicebank.validation.AgeValid;
import com.springboot.practicebank.validation.GenderValid;
import com.springboot.practicebank.validation.UniqueEmailValid;
import com.springboot.practicebank.validation.marker.OnCreate;
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

    @NotBlank(groups = OnCreate.class)
    private String firstName;

    @NotBlank(groups = OnCreate.class)
    private String lastName;

    @GenderValid(groups = OnCreate.class)
    private String gender;

    @NotBlank(groups = OnCreate.class)
    private String address;

    @NotBlank(groups = OnCreate.class)
    private String phoneNumber;

    @AgeValid(groups = OnCreate.class)
    private Integer age;

    @Email
    @UniqueEmailValid(groups = OnUpdate.class)
    @NotBlank(groups = OnCreate.class)
    private String email;

    @NotBlank(groups = OnCreate.class)
    @Size(min = 8, max = 20, message = "Password length must be between 8 and 20 characters")
    private String password;

    private String accountNumber;

    @NotBlank(groups = OnCreate.class)
    @Size(min = 4, max = 4, message = "Pin Number length must only contains 4 digit number")
    @Pattern(regexp = "\\d{4}", message = "Pin Number must be a 4 digit number")
    private String atmPin;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Role role;

    private BigDecimal accountBalance;




}
