package com.springboot.practicebank.service;

import com.springboot.practicebank.dto.*;
import com.springboot.practicebank.entity.Status;
import com.springboot.practicebank.entity.User;
import com.springboot.practicebank.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService{

    private final UserRepository userRepository;
    private final TransactionService transactionService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public BankResponse creditAccount(CreditRequest creditRequest) {

        User creditUser = Optional.ofNullable(userRepository.findByAccountNumber(creditRequest.getAccountNumber()))
                .orElseThrow(() -> new EntityNotFoundException("Invalid Account Number: " + creditRequest.getAccountNumber()));

        BigDecimal getAmount = creditRequest.getAmount();
        BigDecimal accountBalance = creditUser.getAccountBalance();

        BigDecimal newBalance = accountBalance.add(getAmount);

        creditUser.setAccountBalance(newBalance);
        userRepository.save(creditUser);

        TransactionDto transaction = TransactionDto.builder()
                .transactionType("CREDIT")
                .accountNumber(creditUser.getAccountNumber())
                .amount(creditRequest.getAmount())
                .status("SUCCESSFUL")
                .build();
        transactionService.saveTransaction(transaction);

        return BankResponse.builder()
                .responseMessage("Transaction Successful!!!")
                .accountInfo(AccountInfo.builder()
                        .accountNumber(creditUser.getAccountNumber())
                        .accountBalance(creditUser.getAccountBalance())
                        .amount(creditRequest.getAmount())
                        .build())
                .build();
    }

    @Override
    public BankResponse atmDebitAccount(AtmDebitRequest atmDebitRequest) {

        Boolean userAccountNumber = userRepository.existsByAccountNumber(atmDebitRequest.getUserAccountNumber());
        Boolean atmPinNumber = userRepository.existsByAtmPin(atmDebitRequest.getUserPinNumber());

        if(!userAccountNumber || !atmPinNumber){
            throw new BadCredentialsException("Invalid Credentials");
        }

        User debitUser = userRepository.findByAccountNumber(atmDebitRequest.getUserAccountNumber());
        BigDecimal getAmount = atmDebitRequest.getAmount();
        BigDecimal accountBalance = debitUser.getAccountBalance();

        if (getAmount.compareTo(accountBalance) > 0) {
            return BankResponse.builder()
                    .responseMessage("Insufficient Funds!!!")
                    .accountInfo(AccountInfo.builder()
                            .accountNumber(debitUser.getAccountNumber())
                            .accountBalance(debitUser.getAccountBalance())
                            .build())
                    .build();
        }
        else {
            BigDecimal newBalance = accountBalance.subtract(getAmount);
            debitUser.setAccountBalance(newBalance);
            userRepository.save(debitUser);

            TransactionDto transaction = TransactionDto.builder()
                    .transactionType("DEBIT")
                    .accountNumber(debitUser.getAccountNumber())
                    .amount(atmDebitRequest.getAmount())
                    .status("SUCCESSFUL")
                    .build();
            transactionService.saveTransaction(transaction);
        }
        return BankResponse.builder()
                .responseMessage("Transaction Successful!!!")
                .accountInfo(AccountInfo.builder()
                        .accountNumber(debitUser.getAccountNumber())
                        .accountBalance(debitUser.getAccountBalance())
                        .amount(atmDebitRequest.getAmount())
                        .build())
                .build();
    }

    @Override
    public BankResponse transfer(TransferRequest transferRequest) {

        boolean destinationAccountNumber = userRepository.existsByAccountNumber(transferRequest.getDestinationAccountNumber());
        boolean sourceAccountNumber = userRepository.existsByAccountNumber(transferRequest.getSourceAccountNumber());

        if (!destinationAccountNumber || !sourceAccountNumber) {
            throw new EntityNotFoundException("Account Does not Exist!!!");
        }

        User sourceAccount = userRepository.findByAccountNumber(transferRequest.getSourceAccountNumber());
        BigDecimal getAmount = transferRequest.getAmount();
        BigDecimal sourceAccountBalance = sourceAccount.getAccountBalance();

        if (getAmount.compareTo(sourceAccountBalance) > 0) {
            return BankResponse.builder()
                    .responseMessage("Insufficient Funds!!!")
                    .accountInfo(AccountInfo.builder()
                            .accountNumber(sourceAccount.getAccountNumber())
                            .accountBalance(sourceAccount.getAccountBalance())
                            .build())
                    .build();
        } else {
            BigDecimal sourceNewBalance = sourceAccountBalance.subtract(getAmount);
            sourceAccount.setAccountBalance(sourceNewBalance);
            userRepository.save(sourceAccount);

            TransactionDto transaction1 = TransactionDto.builder()
                    .transactionType("DEBIT")
                    .accountNumber(sourceAccount.getAccountNumber())
                    .amount(transferRequest.getAmount())
                    .status("SUCCESSFUL")
                    .build();
            transactionService.saveTransaction(transaction1);


            User destinationAccount = userRepository.findByAccountNumber(transferRequest.getDestinationAccountNumber());
            BigDecimal destinationNewBalance = destinationAccount.getAccountBalance().add(getAmount);
            destinationAccount.setAccountBalance(destinationNewBalance);
            userRepository.save(destinationAccount);

            TransactionDto transaction2 = TransactionDto.builder()
                    .transactionType("CREDIT")
                    .accountNumber(destinationAccount.getAccountNumber())
                    .amount(transferRequest.getAmount())
                    .status("SUCCESSFUL")
                    .build();
            transactionService.saveTransaction(transaction2);


            return BankResponse.builder()
                    .responseMessage("Transfer Successful!!!")
                    .accountInfo(AccountInfo.builder()
                            .accountNumber(destinationAccount.getAccountNumber())
                            .accountBalance(destinationAccount.getAccountBalance())
                            .amount(transferRequest.getAmount())
                            .build())
                    .build();
        }
    }

    @Override
    public BankResponse freezeAccount(InquiryRequest request) {

        User freezeAccount = Optional.ofNullable(userRepository.findByAccountNumber(request.getAccountNumber()))
                .orElseThrow(() -> new EntityNotFoundException("Invalid Account Number: " + request.getAccountNumber()));

        if(!freezeAccount.isFrozen()) {

            freezeAccount.setStatus(Status.valueOf("FROZEN"));
            freezeAccount.setFrozen(true);
            userRepository.save(freezeAccount);

            return BankResponse.builder()
                    .responseMessage("ACCOUNT IS FROZEN")
                    .accountInfo(AccountInfo.builder()
                            .accountNumber(freezeAccount.getAccountNumber())
                            .build())
                    .build();
        }
        else{
            freezeAccount.setStatus(Status.valueOf("ACTIVE"));
            freezeAccount.setFrozen(false);
            userRepository.save(freezeAccount);

            return BankResponse.builder()
                    .responseMessage("ACCOUNT IS ACTIVE")
                    .accountInfo(AccountInfo.builder()
                            .accountNumber(freezeAccount.getAccountNumber())
                            .accountBalance(freezeAccount.getAccountBalance())
                            .build())
                    .build();
        }
    }

    @Override
    public BankResponse changePassword(ChangePasswordRequest request, Principal user) {

        User accountNumber = Optional.ofNullable(userRepository.findByAccountNumber(request.getAccountNumber()))
                .orElseThrow(() -> new EntityNotFoundException("Invalid Account Number: " + request.getAccountNumber()));

        User newPassword = (User) ((UsernamePasswordAuthenticationToken) user).getPrincipal();  //cast the User and UsernamePasswordAuthenticationToken

        //if(request.getCurrentPassword().isEmpty() || request.getNewPassword().isEmpty() || request.getConfirmationPassword().isEmpty()){
        //    throw new BadCredentialsException("Input password");
        //}
        //if(request.getNewPassword().length() < 8 || request.getNewPassword().length() > 20){
        //    throw new IllegalArgumentException("Password length must be between 8 and 20 characters");
        //}
        if(!passwordEncoder.matches(request.getCurrentPassword(), newPassword.getPassword())){
            throw new BadCredentialsException("Wrong Password!");
        }
        if(!request.getNewPassword().matches(request.getConfirmationPassword())){
            throw new BadCredentialsException("Password does not match!");
        }

        newPassword.setPassword(passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(newPassword);

        return BankResponse.builder()
                .responseMessage("Change Password Successful!!!")
                .accountInfo(null)
                .build();
    }

    @Override
    public UpdateUserDto updateUserInfo(UserDto userDto) {

        User updateInfo = Optional.ofNullable(userRepository.findByAccountNumber(userDto.getAccountNumber()))
                .orElseThrow(() -> new EntityNotFoundException("Invalid Account Number: " + userDto.getAccountNumber()));

        if (userDto.getFirstName() != null) {
            updateInfo.setFirstName(userDto.getFirstName());
        }
        if (userDto.getLastName() != null) {
            updateInfo.setLastName(userDto.getLastName());
        }
        if (userDto.getAddress() != null) {
            updateInfo.setAddress(userDto.getAddress());
        }
        if (userDto.getPhoneNumber() != null) {
            updateInfo.setPhoneNumber(userDto.getPhoneNumber());
        }
        if (userDto.getEmail() != null) {
            //boolean checkEmail = userRepository.existsByEmail(userDto.getEmail());
            //if(!checkEmail) {
                updateInfo.setEmail(userDto.getEmail());
            //}
        }

        userRepository.save(updateInfo);

        return UpdateUserDto.builder()
                .firstName(updateInfo.getFirstName())
                .lastName(updateInfo.getLastName())
                .address(updateInfo.getAddress())
                .phoneNumber(updateInfo.getPhoneNumber())
                .email(updateInfo.getEmail())
                .build();
    }

    @Override
    public BankResponse deleteAccount(InquiryRequest request) {

        User deleteUser = Optional.ofNullable(userRepository.findByAccountNumber(request.getAccountNumber()))
                .orElseThrow(() -> new EntityNotFoundException("Invalid Account Number: " + request.getAccountNumber()));

        userRepository.delete(deleteUser);
        return BankResponse.builder()
                .responseMessage("Account Deleted Successfully!!!")
                .accountInfo(null)
                .build();
    }

    @Override
    public BankResponse balanceInquiry(InquiryRequest request) {

        User balanceInquiry = Optional.ofNullable(userRepository.findByAccountNumber(request.getAccountNumber()))
                .orElseThrow(() -> new EntityNotFoundException("Invalid Account Number: " + request.getAccountNumber()));

        return BankResponse.builder()
                .responseMessage("Balance Inquiry")
                .accountInfo(AccountInfo.builder()
                        .accountNumber(balanceInquiry.getAccountNumber())
                        .accountBalance(balanceInquiry.getAccountBalance())
                        .build())
                .build();
    }
}
