package com.springboot.practicebank.service;

import com.springboot.practicebank.model.*;

import java.security.Principal;

public interface AccountService {

    BankResponse creditAccount(CreditRequest creditRequest);
    BankResponse atmDebitAccount(AtmDebitRequest atmDebitRequest);
    BankResponse transfer(TransferRequest transferRequest);
    BankResponse balanceInquiry(InquiryRequest request);
    BankResponse deleteAccount(InquiryRequest request);
    UpdateUserModel updateUserInfo(UserModel userModel);
    BankResponse changePassword(ChangePasswordRequest request, Principal user);
    BankResponse freezeAccount(InquiryRequest request);

}
