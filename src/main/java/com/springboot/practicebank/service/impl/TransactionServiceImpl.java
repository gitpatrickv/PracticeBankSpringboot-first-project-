package com.springboot.practicebank.service.impl;

import com.springboot.practicebank.entity.Transaction;
import com.springboot.practicebank.model.InquiryRequest;
import com.springboot.practicebank.model.TransactionModel;
import com.springboot.practicebank.repository.TransactionRepository;
import com.springboot.practicebank.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    @Override
    public void saveTransaction(TransactionModel transactionModel) {

        Transaction saveTransaction = Transaction.builder()
                .transactionType(transactionModel.getTransactionType())
                .accountNumber(transactionModel.getAccountNumber())
                .amount(transactionModel.getAmount())
                .status(transactionModel.getStatus())
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
