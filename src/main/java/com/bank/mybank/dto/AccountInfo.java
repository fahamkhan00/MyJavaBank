package com.bank.mybank.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class AccountInfo {
	
	@Schema(
			name="User Account Name"
			)
	
	private String accountName;
	

	@Schema(
			name="User Account Number"
	)
	
	private String accountNumber;
	

	@Schema(
			name="User Account Balance"
		)
	
	private BigDecimal accountBalance;

}
