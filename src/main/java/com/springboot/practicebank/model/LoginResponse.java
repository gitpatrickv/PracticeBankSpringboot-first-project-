package com.springboot.practicebank.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class LoginResponse {

    private String jwtToken;
    private String refreshToken;
    private String email;
    private String responseMessage;
}
