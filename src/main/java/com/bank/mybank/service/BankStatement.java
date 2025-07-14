package com.bank.mybank.service;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Component;

import com.bank.mybank.entity.Transaction;
import com.bank.mybank.repository.TransactionRepository;
import com.itextpdf.awt.geom.Rectangle;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.codec.Base64.OutputStream;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j//temp
public class BankStatement {
	
	//retrieve list of transaction within a date range of given account number
	//generate pdf of transaction
	//send file via email
	
	private TransactionRepository transactionRepository;
	
	private static final String FILE="//home//faham//Documents/statement.pdf";
	
	public List<Transaction> generateStatement(String accountNumber, String startDate, String endDate) throws FileNotFoundException{
		
		LocalDate start =LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE);
		
		LocalDate end =LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE);

		
		List<Transaction> transactionList=transactionRepository.findAll().stream()
				.filter(transaction -> transaction.getAccountNumber().equals(accountNumber))
				.filter(transaction -> transaction.getCreatedAt().isEqual(start))
				.filter(transaction -> transaction.getCreatedAt().isEqual(end)).toList();
		

		return transactionList;	
		
	}
	
	private void designStatement(List<Transaction> transactions) {
				
	}

}
