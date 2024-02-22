package com.springboot.practicebank.dto;

import com.springboot.practicebank.entity.constants.Gender;
import com.springboot.practicebank.entity.constants.Role;
import com.springboot.practicebank.entity.constants.Status;
import com.springboot.practicebank.validation.AgeValid;
import com.springboot.practicebank.validation.UniqueEmailValid;
import com.springboot.practicebank.validation.marker.OnCreate;
import com.springboot.practicebank.validation.marker.OnUpdate;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    @NotBlank(groups = OnCreate.class, message = "{firstname.required}")
    private String firstName;
    @NotBlank(groups = OnCreate.class, message = "{lastname.required}")
    private String lastName;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @NotBlank(groups = OnCreate.class, message = "{address.required}")
    private String address;
    @NotBlank(groups = OnCreate.class, message = "{phone.number.required}")
    private String phoneNumber;
    @AgeValid(groups = OnCreate.class)
    private Integer age;
    @UniqueEmailValid(groups = {OnCreate.class, OnUpdate.class})
    @Email(groups = {OnCreate.class, OnUpdate.class})
    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "{email.required}")
    private String email;

    @NotBlank(groups = OnCreate.class, message = "{password.required}")
    private String password;

    private String accountNumber;

    @NotBlank(groups = OnCreate.class)
    @Size(min = 4, max = 4, message = "{pin.number.invalid}", groups = OnCreate.class)
    @Pattern(regexp = "\\d{4}", message = "{pin.number.size}", groups = OnCreate.class)
    private String atmPin;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Role role;

    private BigDecimal accountBalance;


}
