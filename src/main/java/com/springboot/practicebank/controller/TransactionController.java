package com.springboot.practicebank.controller;

import com.springboot.practicebank.entity.Transaction;
import com.springboot.practicebank.model.InquiryRequest;
import com.springboot.practicebank.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/history")
    @ResponseStatus(HttpStatus.OK)
    public List<Transaction> getAllTransaction(@RequestBody @Valid InquiryRequest inquiryRequest) {
        return transactionService.getAllTransaction(inquiryRequest);
    }
}
