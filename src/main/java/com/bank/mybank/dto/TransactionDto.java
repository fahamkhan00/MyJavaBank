package com.bank.mybank.dto;

import java.math.BigDecimal;

import com.bank.mybank.entity.Transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {
	private String transactionType;
	private BigDecimal amount;
	private String accountNumber;
	private String status;
	

}
