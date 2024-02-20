package com.springboot.practicebank.repository;

import com.springboot.practicebank.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

    Boolean existsByAccountNumber(String accountNumber);
    Transaction findByAccountNumber(String accountNumber);

}
