package com.springboot.practicebank.service.impl;


import com.springboot.practicebank.entity.User;
import com.springboot.practicebank.model.*;
import com.springboot.practicebank.repository.UserRepository;
import com.springboot.practicebank.security.JwtTokenProvider;
import com.springboot.practicebank.service.UserService;
import com.springboot.practicebank.utils.AccountUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public BankResponse createUser(UserModel userModel) {

        boolean isEmailExists = userRepository.existsByEmail(userModel.getEmail());
        boolean isAccountNumberExists = userRepository.existsByAccountNumber(userModel.getAccountNumber());

        if (isEmailExists || isAccountNumberExists) {
            return BankResponse.builder()
                    .responseMessage("Account already exist!!!")
                    .build();
        }

        User newUser = User.builder()
                .firstName(userModel.getFirstName())
                .lastName(userModel.getLastName())
                .gender(userModel.getGender())
                .address(userModel.getAddress())
                .phoneNumber(userModel.getPhoneNumber())
                .age(userModel.getAge())
                .email(userModel.getEmail())
                .accountNumber(AccountUtils.generateAccountNumber())
                .accountBalance(BigDecimal.ZERO)
                .password(passwordEncoder.encode(userModel.getPassword()))
                .atmPin(userModel.getAtmPin())
                .status(userModel.getStatus())
                .role(userModel.getRole())
                .build();

        userRepository.save(newUser);

        return BankResponse.builder()
                .responseMessage("Account Created Successfully!!!")
                .accountInfo(AccountInfo.builder()
                        .accountNumber(newUser.getAccountNumber())
                        .accountBalance(newUser.getAccountBalance())
                        .build())
                .build();
    }

    @Override
    public LoginResponse userLogin(LoginRequest loginRequest) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );

            return LoginResponse.builder()
                    .jwtToken(jwtTokenProvider.generateToken(authentication))
                    //.refreshToken(jwtTokenProvider.generateRefreshToken(new HashMap<>(), authentication))
                    .email(loginRequest.getEmail())
                    .responseMessage("Login Successful!!!")
                    .build();
        }
        catch (AuthenticationException e) {
            throw new BadCredentialsException("Login Failed: Invalid Credentials");
        }
    }
}





