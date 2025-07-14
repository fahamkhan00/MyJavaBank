package com.bank.mybank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.mybank.dto.TransactionDto;
import com.bank.mybank.entity.Transaction;
import com.bank.mybank.repository.TransactionRepository;

@Service
public class TransactionImplementation implements TransactionService {
	
	@Autowired
	TransactionRepository transactionRepository;


	@Override
	public void saveTransaction(TransactionDto transactionDto) {
		// TODO Auto-generated method stub
		Transaction transaction=Transaction.builder()
				.transactionType(transactionDto.getTransactionType())
				.accountNumber(transactionDto.getAccountNumber())
				.amount(transactionDto.getAmount())
				.status("SUCCESS")
				.build();
		transactionRepository.save(transaction);
		System.out.println("Transaction Saved Successfully");
		
	}

}
