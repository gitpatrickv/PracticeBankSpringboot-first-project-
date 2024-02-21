package com.springboot.practicebank.controller;

import com.springboot.practicebank.dto.BankResponse;
import com.springboot.practicebank.dto.LoginRequest;
import com.springboot.practicebank.dto.LoginResponse;
import com.springboot.practicebank.dto.UserDto;
import com.springboot.practicebank.service.UserService;
import com.springboot.practicebank.validation.marker.OnCreate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public BankResponse createUser(@RequestBody @Validated(OnCreate.class) UserDto userDto){
        return userService.createUser(userDto);
        }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse userLogin(@RequestBody @Valid LoginRequest loginRequest){
        return userService.userLogin(loginRequest);
    }



}
