package com.bank.mybank.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bank.mybank.entity.Transaction;
import com.bank.mybank.service.BankStatement;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(path="/bankStatement")
@Tag(name="User Transaction Management API's")
public class TransactionController {
	
	@Autowired
	private BankStatement bankStatement;
	
	@GetMapping
	public List<Transaction> generateStatement(@RequestParam String accountNumber,
												@RequestParam String startDate,
												@RequestParam String endDate){
		
		return bankStatement.generateStatement(accountNumber, startDate, endDate);
		
	}
	

}
