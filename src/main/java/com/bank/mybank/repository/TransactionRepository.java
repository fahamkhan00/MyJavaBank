package com.bank.mybank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.mybank.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, String> {

}
