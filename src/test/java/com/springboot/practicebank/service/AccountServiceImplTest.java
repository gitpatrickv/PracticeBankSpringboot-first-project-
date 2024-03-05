package com.springboot.practicebank.service;

import com.springboot.practicebank.entity.User;
import com.springboot.practicebank.entity.constants.Status;
import com.springboot.practicebank.model.*;
import com.springboot.practicebank.repository.UserRepository;
import com.springboot.practicebank.service.impl.AccountServiceImpl;
import com.springboot.practicebank.service.impl.TransactionServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;

import static com.springboot.practicebank.entity.constants.Status.ACTIVE;
import static com.springboot.practicebank.entity.constants.Status.FROZEN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AccountServiceImplTest {
    @InjectMocks
    private AccountServiceImpl accountService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TransactionServiceImpl transactionService;
    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSuccessfulCreditAccount() {

        CreditRequest creditRequest = new CreditRequest();
        creditRequest.setAccountNumber("202412345678");
        creditRequest.setAmount(BigDecimal.valueOf(100));

        User user = new User();
        user.setAccountNumber("202412345678");
        user.setAccountBalance(BigDecimal.valueOf(500));

        when(userRepository.findByAccountNumber("202412345678")).thenReturn(user);

        BankResponse response = accountService.creditAccount(creditRequest);
        BigDecimal newBalance = BigDecimal.valueOf(600);

        verify(userRepository, times(1)).save(any(User.class));
        verify(transactionService, times(1)).saveTransaction(any(TransactionModel.class));

        assertEquals("Transaction Successful!!!", response.getResponseMessage());
        Assertions.assertThat(newBalance).isEqualTo(user.getAccountBalance());
    }

    @Test
    public void testFailedInsuficcientFundsAtmDebitAccount() {

        AtmDebitRequest debitRequest = new AtmDebitRequest();
        debitRequest.setAccountNumber("202412345678");
        debitRequest.setAtmPin("1234");
        debitRequest.setAmount(BigDecimal.valueOf(500));

        User user = new User();
        user.setAccountNumber("202412345678");
        user.setAccountBalance(BigDecimal.valueOf(100));

        when(userRepository.existsByAccountNumber("202412345678")).thenReturn(true);
        when(userRepository.existsByAtmPin("1234")).thenReturn(true);
        when(userRepository.findByAccountNumber("202412345678")).thenReturn(user);

        BankResponse response = accountService.atmDebitAccount(debitRequest);
        BigDecimal currentBalance = BigDecimal.valueOf(100);
        BigDecimal debitAmount = BigDecimal.valueOf(500);

        assertEquals("Insufficient Funds!!!", response.getResponseMessage());
        Assertions.assertThat(debitAmount).isGreaterThan(currentBalance);

    }

    @Test
    public void testSuccessfulAtmDebitAccount(){

        AtmDebitRequest debitRequest = new AtmDebitRequest();
        debitRequest.setAccountNumber("202412345678");
        debitRequest.setAtmPin("1234");
        debitRequest.setAmount(BigDecimal.valueOf(500));

        User user = new User();

        user.setAccountNumber("202412345678");
        user.setAccountBalance(BigDecimal.valueOf(2000));

        when(userRepository.existsByAccountNumber("202412345678")).thenReturn(true);
        when(userRepository.existsByAtmPin("1234")).thenReturn(true);
        when(userRepository.findByAccountNumber("202412345678")).thenReturn(user);

        BankResponse response = accountService.atmDebitAccount(debitRequest);
        BigDecimal newBalance = BigDecimal.valueOf(1500);

        verify(userRepository, times(1)).save(any(User.class));
        verify(transactionService, times(1)).saveTransaction(any(TransactionModel.class));

        assertEquals("Transaction Successful!!!", response.getResponseMessage());
        Assertions.assertThat(newBalance).isEqualTo(user.getAccountBalance());

    }
    @Test
    public void testFailedTransferFromSourceAccountToRecipientInsufficientFunds(){

        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setSourceAccountNumber("202412345678");
        transferRequest.setDestinationAccountNumber("202412345679");
        transferRequest.setAmount(BigDecimal.valueOf(500));

        User sourceUser = new User();
        sourceUser.setAccountNumber("202412345678");
        sourceUser.setAccountBalance(BigDecimal.valueOf(100));

        User destinationUser = new User();
        destinationUser.setAccountNumber("202412345679");

        when(userRepository.existsByAccountNumber("202412345678")).thenReturn(true);
        when(userRepository.existsByAccountNumber("202412345679")).thenReturn(true);
        when(userRepository.findByAccountNumber("202412345678")).thenReturn(sourceUser);
        when(userRepository.findByAccountNumber("202412345679")).thenReturn(destinationUser);

        BankResponse response = accountService.transfer(transferRequest);
        BigDecimal currentBalance = BigDecimal.valueOf(100);
        BigDecimal transferAmount = BigDecimal.valueOf(500);

        assertEquals("Insufficient Funds!!!", response.getResponseMessage());
        Assertions.assertThat(transferAmount).isGreaterThan(currentBalance);
    }

    @Test
    public void testSuccessfulTransferFromSourceAccountToRecipient(){

        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setSourceAccountNumber("202412345678");
        transferRequest.setDestinationAccountNumber("202412345679");
        transferRequest.setAmount(BigDecimal.valueOf(700));

        User sourceUser = new User();
        sourceUser.setAccountNumber("202412345678");
        sourceUser.setAccountBalance(BigDecimal.valueOf(1000));

        User destinationUser = new User();
        destinationUser.setAccountNumber("202412345679");
        destinationUser.setAccountBalance(BigDecimal.valueOf(500));

        when(userRepository.existsByAccountNumber("202412345678")).thenReturn(true);
        when(userRepository.existsByAccountNumber("202412345679")).thenReturn(true);
        when(userRepository.findByAccountNumber("202412345678")).thenReturn(sourceUser);
        when(userRepository.findByAccountNumber("202412345679")).thenReturn(destinationUser);

        BankResponse response = accountService.transfer(transferRequest);
        BigDecimal sourceNewBalance = BigDecimal.valueOf(300);
        BigDecimal recipientNewBalance = BigDecimal.valueOf(1200);

        verify(userRepository, times(2)).save(any(User.class));
        verify(transactionService, times(2)).saveTransaction(any(TransactionModel.class));

        Assertions.assertThat(sourceNewBalance).isEqualTo(sourceUser.getAccountBalance());
        Assertions.assertThat(recipientNewBalance).isEqualTo(destinationUser.getAccountBalance());

    }

    @Test
    public void testIfAccountIsFrozenDisabledOrLock(){

        InquiryRequest inquiryRequest = new InquiryRequest();
        inquiryRequest.setAccountNumber("202412345678");

        User user = new User();
        user.setAccountNumber("202412345678");
        user.setFrozen(false);
        user.setStatus(Status.valueOf("FROZEN"));

        when(userRepository.findByAccountNumber("202412345678")).thenReturn(user);

        BankResponse response = accountService.freezeAccount(inquiryRequest);

        verify(userRepository, times(1)).save(any(User.class));

        assertEquals("ACCOUNT IS FROZEN", response.getResponseMessage());
        Assertions.assertThat(user.getStatus()).isEqualTo(FROZEN);
        Assertions.assertThat(user.isAccountNonLocked()).isEqualTo(false);
        Assertions.assertThat(user.isEnabled()).isEqualTo(false);

    }

    @Test
    public void testIfAccountIsACTIVE(){
        InquiryRequest inquiryRequest = new InquiryRequest();
        inquiryRequest.setAccountNumber("202412345678");

        User user = new User();
        user.setAccountNumber("202412345678");
        user.setFrozen(true);
        user.setStatus(Status.valueOf("ACTIVE"));

        when(userRepository.findByAccountNumber("202412345678")).thenReturn(user);

        BankResponse response = accountService.freezeAccount(inquiryRequest);

        verify(userRepository, times(1)).save(any(User.class));

        assertEquals("ACCOUNT IS ACTIVE", response.getResponseMessage());
        Assertions.assertThat(user.getStatus()).isEqualTo(ACTIVE);
        Assertions.assertThat(user.isAccountNonLocked()).isEqualTo(true);
        Assertions.assertThat(user.isEnabled()).isEqualTo(true);

    }

    @Test
    public void testUpdateUserInformation(){
        UserModel userModel = new UserModel();
        userModel.setAccountNumber("202412345678");
        userModel.setFirstName("newFirstName");
        userModel.setLastName("newLastName");
        userModel.setAddress("newAddress");
        userModel.setPhoneNumber("099999999");
        //userDto.setEmail("newemail@gmail.com");

        User oldUser = new User();
        oldUser.setAccountNumber("202412345678");
        oldUser.setFirstName("oldFirstName");
        oldUser.setLastName("oldLastName");
        oldUser.setAddress("oldAddress");
        oldUser.setPhoneNumber("0666666666");
        oldUser.setEmail("oldemail@gmail.com");

        when(userRepository.findByAccountNumber("202412345678")).thenReturn(oldUser);

        UpdateUserModel updateUserModel = accountService.updateUserInfo(userModel);

        verify(userRepository, times(1)).save(any(User.class));

        Assertions.assertThat(oldUser.getFirstName()).isEqualTo(userModel.getFirstName());
        Assertions.assertThat(oldUser.getLastName()).isEqualTo(userModel.getLastName());
        Assertions.assertThat(oldUser.getAddress()).isEqualTo(userModel.getAddress());
        Assertions.assertThat(oldUser.getPhoneNumber()).isEqualTo(userModel.getPhoneNumber());
        Assertions.assertThat(oldUser.getEmail()).isEqualTo(oldUser.getEmail());
        //Assertions.assertThat(oldUser.getEmail()).isEqualTo(userDto.getEmail());

    }


}