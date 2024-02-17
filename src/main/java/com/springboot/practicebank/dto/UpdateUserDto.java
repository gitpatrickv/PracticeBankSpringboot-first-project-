package com.springboot.practicebank.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserDto {

    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private String email;

}
