package com.springboot.practicebank.service;

import com.springboot.practicebank.dto.InquiryRequest;
import com.springboot.practicebank.dto.TransactionDto;
import com.springboot.practicebank.entity.Transaction;

import java.util.List;

public interface TransactionService {

    void saveTransaction(TransactionDto transactionDto);

    List<Transaction> getAllTransaction(InquiryRequest inquiryRequest);
}
