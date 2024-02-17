package com.springboot.practicebank.service;

import com.springboot.practicebank.dto.InquiryRequest;
import com.springboot.practicebank.dto.TransactionDto;
import com.springboot.practicebank.entity.Transaction;
import com.springboot.practicebank.repository.TransactionRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

class TransactionServiceImplTest {

    @Mock
    TransactionRepository transactionRepository;
    @InjectMocks
    TransactionServiceImpl transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveTransaction() {

        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setAccountNumber("202412345678");
        transactionDto.setTransactionType("CREDIT");
        transactionDto.setAmount(BigDecimal.valueOf(500));
        transactionDto.setStatus("SUCCESSFUL");

        transactionService.saveTransaction(transactionDto);

        ArgumentCaptor<Transaction> argumentCaptor = ArgumentCaptor.forClass(Transaction.class);
        verify(transactionRepository, times(1)).save(argumentCaptor.capture());

        Transaction capturedTransaction = argumentCaptor.getValue();
        Assertions.assertThat(capturedTransaction).isNotNull();
        Assertions.assertThat(capturedTransaction.getTransactionType()).isEqualTo(transactionDto.getTransactionType());
        Assertions.assertThat(capturedTransaction.getAmount()).isEqualTo(transactionDto.getAmount());
        Assertions.assertThat(capturedTransaction.getStatus()).isEqualTo(transactionDto.getStatus());
        Assertions.assertThat(capturedTransaction.getAccountNumber()).isEqualTo(transactionDto.getAccountNumber());

    }

    @Test
    void getAllTransaction() {
        InquiryRequest request = new InquiryRequest();
        request.setAccountNumber("202412345678");

        LocalDateTime localDateTime = LocalDateTime.now();

        Transaction transaction1 = new Transaction("123", "CREDIT", BigDecimal.valueOf(5000),
                "202412345678", "SUCCESSFUL", localDateTime);

        Transaction transaction2 = new Transaction("456", "DEBIT", BigDecimal.valueOf(1000),
                "202412345678", "SUCCESSFUL", localDateTime);

        Transaction transaction3 = new Transaction("789", "CREDIT", BigDecimal.valueOf(3000),
                "202412345678", "SUCCESSFUL", localDateTime);

        when(transactionRepository.findAll()).thenReturn(Arrays.asList(transaction1, transaction2, transaction3));

        List<Transaction> findAllTransaction = transactionService.getAllTransaction(request);

        Assertions.assertThat(findAllTransaction).isNotNull();
        Assertions.assertThat(findAllTransaction).containsExactlyInAnyOrder(transaction2,transaction1,transaction3);


    }
}