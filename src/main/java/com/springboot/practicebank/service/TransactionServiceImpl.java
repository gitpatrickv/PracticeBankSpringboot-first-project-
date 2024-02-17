package com.springboot.practicebank.service;

import com.springboot.practicebank.dto.InquiryRequest;
import com.springboot.practicebank.dto.TransactionDto;
import com.springboot.practicebank.entity.Transaction;
import com.springboot.practicebank.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService{

    private final TransactionRepository transactionRepository;
    @Override
    public void saveTransaction(TransactionDto transactionDto) {

        Transaction saveTransaction = Transaction.builder()
                .transactionType(transactionDto.getTransactionType())
                .accountNumber(transactionDto.getAccountNumber())
                .amount(transactionDto.getAmount())
                .status(transactionDto.getStatus())
                .build();

        transactionRepository.save(saveTransaction);
    }

    @Override
    public List<Transaction> getAllTransaction(InquiryRequest inquiryRequest) {

        return transactionRepository.findAll()
                .stream()
                .filter(transaction -> transaction.getAccountNumber().equals(inquiryRequest.getAccountNumber()))
                .toList();

    }
}
