package com.bank.mybank.dto;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


//@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankResponse {
	
	private String responseCode;
	
	private String responseMessage;
	
	private AccountInfo accountInfo;

}
