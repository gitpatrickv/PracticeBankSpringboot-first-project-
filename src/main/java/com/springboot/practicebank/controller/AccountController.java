package com.springboot.practicebank.controller;

import com.springboot.practicebank.dto.*;
import com.springboot.practicebank.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/credit")
    @ResponseStatus(HttpStatus.OK)
    public BankResponse creditAccount(@RequestBody @Valid CreditRequest creditRequest) {
        return accountService.creditAccount(creditRequest);
    }
    @PostMapping("/debit")
    @ResponseStatus(HttpStatus.OK)
    public BankResponse atmDebitAccount(@RequestBody @Valid AtmDebitRequest atmDebitRequest) {
        return accountService.atmDebitAccount(atmDebitRequest);
    }
    @PostMapping("/transfer")
    @ResponseStatus(HttpStatus.OK)
    public BankResponse transfer(@RequestBody @Valid TransferRequest transferRequest){
        return accountService.transfer(transferRequest);
    }
    @PutMapping("/freeze")
    @ResponseStatus(HttpStatus.OK)
    public BankResponse freezeAccount(@RequestBody @Valid InquiryRequest request) {
        return accountService.freezeAccount(request);
    }
    @PutMapping("/updateInfo")
    @ResponseStatus(HttpStatus.OK)
    public UpdateUserDto updateUserInfo(@RequestBody UserDto userDto){
        return accountService.updateUserInfo(userDto);
    }
    @PutMapping("/changePassword")
    @ResponseStatus(HttpStatus.OK)
    public BankResponse changePassword(@RequestBody @Valid ChangePasswordRequest request, Principal user){
        return accountService.changePassword(request, user);
    }
    @GetMapping("/inquiry")
    @ResponseStatus(HttpStatus.OK)
    public BankResponse balanceInquiry(@RequestBody @Valid InquiryRequest request){
        return accountService.balanceInquiry(request);
    }
    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.GONE)
    public BankResponse deleteAccount(@RequestBody @Valid InquiryRequest request) {
        return accountService.deleteAccount(request);
    }

}
