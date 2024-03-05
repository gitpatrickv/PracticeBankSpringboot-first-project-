package com.springboot.practicebank.service;

import com.springboot.practicebank.model.BankResponse;
import com.springboot.practicebank.model.LoginRequest;
import com.springboot.practicebank.model.LoginResponse;
import com.springboot.practicebank.model.UserModel;

public interface UserService {

    BankResponse createUser(UserModel userModel);
    LoginResponse userLogin(LoginRequest loginRequest);

}
