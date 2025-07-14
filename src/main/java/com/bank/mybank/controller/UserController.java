package com.bank.mybank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.mybank.dto.BankResponse;
import com.bank.mybank.dto.CreditDebitRequest;
import com.bank.mybank.dto.EnquiryRequest;
import com.bank.mybank.dto.TransferRequest;
import com.bank.mybank.dto.UserRequest;
import com.bank.mybank.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(path="/api/user")
@Tag(name="User Account Management API's")
public class UserController {

	
	@Autowired
	UserService userService;
	@Operation(
			summary="Create New User Account",
			description="Creating a new user and assigning an account ID"
			)
	@ApiResponse(
			responseCode="201",
			description="Http Status 201 Created"
			)
	@PostMapping()
	public BankResponse createAccount(@RequestBody UserRequest userRequest) {
		return userService.createAccount(userRequest);
	}
	
	@Operation(
			summary="Check Balance",
			description="User Can Check Account Balance"
			)
	@ApiResponse(
			responseCode="202",
			description="Http Status 202 Created"
			)
	@GetMapping("balanceEnquiry")
	public BankResponse balanceEnquiry(@RequestBody EnquiryRequest request) {
		return userService.balanceEnquiry(request);
	}
	
	
	@Operation(
			summary="Check Name",
			description="Check the Account Holder Name"
			)
	@ApiResponse(
			responseCode="203",
			description="Http Status 203 Created"
			)
	@GetMapping("nameEnquiry")
	public String nameEnquiry(@RequestBody EnquiryRequest request) {
		return userService.nameEnquiry(request);
	}
	
	@Operation(
			summary="Credit ",
			description="To credit the amount of the user"
			)
	@ApiResponse(
			responseCode="204",
			description="Http Status 204 Created"
			)
	
	@PostMapping("credit")
	public BankResponse creditAccount(@RequestBody CreditDebitRequest request) {
		return userService.creditAccount(request);
	}
	
	
	@Operation(
			summary="Debit",
			description="To debit the amount of the user"
			)
	@ApiResponse(
			responseCode="205",
			description="Http Status 205 Created"
			)
	
	@PostMapping("debit")
	public BankResponse debitAccount(@RequestBody CreditDebitRequest request) {
		return userService.debitAccount(request);
	}
	
	@Operation(
			summary="Transfer",
			description="User can transfer money to different bank account"
			)
	@ApiResponse(
			responseCode="206",
			description="Http Status 206 Created"
			)
	
	
	@PostMapping("transfer")
	public BankResponse transferAccount(@RequestBody TransferRequest request) {
		return userService.transfer(request);
	}



}
