package com.bank.mybank.service;

import com.bank.mybank.dto.BankResponse;
import com.bank.mybank.dto.CreditDebitRequest;
import com.bank.mybank.dto.EnquiryRequest;
import com.bank.mybank.dto.TransferRequest;
import com.bank.mybank.dto.UserRequest;

public interface UserService {
	
	BankResponse createAccount(UserRequest userRequest);
	
	BankResponse balanceEnquiry(EnquiryRequest request);
	
	String nameEnquiry(EnquiryRequest request);
	
	BankResponse creditAccount(CreditDebitRequest request);
	
	BankResponse debitAccount(CreditDebitRequest request);
	
	BankResponse transfer(TransferRequest request);


}
