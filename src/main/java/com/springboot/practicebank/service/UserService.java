package com.springboot.practicebank.service;

import com.springboot.practicebank.dto.BankResponse;
import com.springboot.practicebank.dto.LoginRequest;
import com.springboot.practicebank.dto.LoginResponse;
import com.springboot.practicebank.dto.UserDto;

public interface UserService {

    BankResponse createUser(UserDto userDto);
    LoginResponse userLogin(LoginRequest loginRequest);

}
