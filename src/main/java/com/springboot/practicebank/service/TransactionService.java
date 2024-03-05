package com.springboot.practicebank.service;

import com.springboot.practicebank.entity.Transaction;
import com.springboot.practicebank.model.InquiryRequest;
import com.springboot.practicebank.model.TransactionModel;

import java.util.List;

public interface TransactionService {

    void saveTransaction(TransactionModel transactionModel);

    List<Transaction> getAllTransaction(InquiryRequest inquiryRequest);
}
