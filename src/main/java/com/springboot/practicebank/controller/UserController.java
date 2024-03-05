package com.springboot.practicebank.controller;

import com.springboot.practicebank.model.BankResponse;
import com.springboot.practicebank.model.LoginRequest;
import com.springboot.practicebank.model.LoginResponse;
import com.springboot.practicebank.model.UserModel;
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
    public BankResponse createUser(@RequestBody @Validated(OnCreate.class) UserModel userModel){
        return userService.createUser(userModel);
        }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse userLogin(@RequestBody @Valid LoginRequest loginRequest){
        return userService.userLogin(loginRequest);
    }
}
