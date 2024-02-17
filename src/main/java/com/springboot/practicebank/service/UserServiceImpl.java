package com.springboot.practicebank.service;


import com.springboot.practicebank.config.JwtTokenProvider;
import com.springboot.practicebank.dto.*;
import com.springboot.practicebank.entity.User;
import com.springboot.practicebank.repository.UserRepository;
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
    public BankResponse createUser(UserDto userDto) {

        boolean isEmailExists = userRepository.existsByEmail(userDto.getEmail());

        if (isEmailExists) {
            return BankResponse.builder()
                    .responseMessage("Account already exist!!!")
                    .build();
        }

        User newUser = User.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .gender(userDto.getGender())
                .address(userDto.getAddress())
                .phoneNumber(userDto.getPhoneNumber())
                .email(userDto.getEmail())
                .accountNumber(AccountUtils.generateAccountNumber())
                .accountBalance(BigDecimal.ZERO)
                .password(passwordEncoder.encode(userDto.getPassword()))
                .atmPin(userDto.getAtmPin())
                .status(userDto.getStatus())
                .role(userDto.getRole())
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





