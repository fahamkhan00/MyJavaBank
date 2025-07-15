package com.bank.mybank.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bank.mybank.config.JwtTokenProvider;
import com.bank.mybank.dto.AccountInfo;
import com.bank.mybank.dto.BankResponse;
import com.bank.mybank.dto.CreditDebitRequest;
import com.bank.mybank.dto.EmailDetails;
import com.bank.mybank.dto.EnquiryRequest;
import com.bank.mybank.dto.LoginDto;
import com.bank.mybank.dto.TransactionDto;
import com.bank.mybank.dto.TransferRequest;
import com.bank.mybank.dto.UserRequest;
import com.bank.mybank.entity.Role;
import com.bank.mybank.entity.User;
import com.bank.mybank.repository.UserRepository;
import com.bank.mybank.utils.AccountUtils;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;


import lombok.AllArgsConstructor;
import lombok.Builder;

@Service
@AllArgsConstructor
public class UserServiceImplementation implements UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	TransactionService transactionService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtTokenProvider jwtTokenProvider;

	
	@Override
	public BankResponse createAccount(UserRequest userRequest) {
		// Creating a New Account--saving new User into db
		//Check if user already exist
		
		if(userRepository.existsByEmail(userRequest.getEmail())) {
			return BankResponse.builder()
				.responseCode(AccountUtils.Account_Exist_Code)
				.responseMessage(AccountUtils.Account_Exist_Message)
				.accountInfo(null)
			.build();
		}
		
		User newUser= User.builder()
				.firstName(userRequest.getFirstName())
				.lastName(userRequest.getLastName())
				.otherName(userRequest.getOtherName())
				.gender(userRequest.getGender())
				.address(userRequest.getAddress())
				.stateofOrigin(userRequest.getStateofOrigin())
				
				.accountNumber(AccountUtils.generateAccountNumber())
				
				.accountBalance(BigDecimal.ZERO)
				
				.email(userRequest.getEmail())
				.password(passwordEncoder.encode( userRequest.getPassword()))
				.phoneNumber(userRequest.getPhoneNumber())
				.alternativePhoneNumber(userRequest.getAlternativePhoneNumber())
				.Status("Active")
				.role(Role.valueOf("ROLE_ADMIN"))
				
				
				
				.build();
		
		User savedUser = userRepository.save(newUser);
		
		EmailDetails emailDetails=EmailDetails.builder()
				.recipient(savedUser.getEmail())
				.subject("Account Created")
				.messageBody("Congratulations "+savedUser.getFirstName()+" "+savedUser.getLastName()+"!! \n Your account has beed successfully created \n"
						+ "Account Number: "+savedUser.getAccountNumber()+"Available Balance: "+savedUser.getAccountBalance())
				
				.build();
		
		emailService.sendEmailAlert(emailDetails);
		
		
		return BankResponse.builder()
				.responseCode(AccountUtils.Account_Created_Success_Code)
				.responseMessage(AccountUtils.Account_Created_Success_Message)
				.accountInfo(AccountInfo.builder()
						.accountBalance(savedUser.getAccountBalance())
						.accountNumber(savedUser.getAccountNumber())
						.accountName(savedUser.getFirstName()+" "+ savedUser.getLastName())
						.build())
				.build();
	}
	
	@Override
	public BankResponse login(LoginDto loginDto) {
		Authentication authentication=null;
		authentication=authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
				);
		
		
		EmailDetails loginAlert=EmailDetails.builder()
				.recipient(loginDto.getEmail())
				.subject("You're Logged in!")
				.messageBody("You're logged in into your account. If you did not initiate this request please "
						+ "contact your bank")
				.build();
		emailService.sendEmailAlert(loginAlert);
		
		return BankResponse.builder()
				.responseCode("Login Success")
				.responseMessage(jwtTokenProvider.generateToken(authentication))
				.accountInfo(null)
				.build();
	}
	
	
	
	
	

	@Override
	public BankResponse balanceEnquiry(EnquiryRequest request) {
		Boolean isAccountExist = userRepository.existsByAccountNumber(request.getAccountNumber());
				if(!isAccountExist) {
					return BankResponse.builder()
							.responseCode(AccountUtils.Account_Not_Exist_Code)
							.responseMessage(AccountUtils.Account_Not_Exist_Message)
							.accountInfo(null)
							.build();
				}
				User foundUser = userRepository.findByAccountNumber(request.getAccountNumber());
				return BankResponse.builder()
						.responseCode(AccountUtils.Account_Found_Code)
						.responseMessage(AccountUtils.Account_Found_Message)
						.accountInfo(AccountInfo.builder()
								.accountName(foundUser.getFirstName()+" "+foundUser.getLastName())
								.accountNumber(foundUser.getAccountNumber())
								.accountBalance(foundUser.getAccountBalance())
								.build())
						.build();
		
				
	}

	@Override
	public String nameEnquiry(EnquiryRequest request) {
		// TODO Auto-generated method stub
		Boolean isAccountExist = userRepository.existsByAccountNumber(request.getAccountNumber());
		if(!isAccountExist) {
			return AccountUtils.Account_Not_Exist_Message;

		}
		
		User foundUser =userRepository.findByAccountNumber(request.getAccountNumber());
		
		return foundUser.getFirstName()+" "+foundUser.getLastName();

	}

	
	
	@Override
	public BankResponse creditAccount(CreditDebitRequest request) {
		Boolean isAccountExist = userRepository.existsByAccountNumber(request.getAccountNumber());
		if(!isAccountExist) {
			return BankResponse.builder()
					.responseCode(AccountUtils.Account_Not_Exist_Code)
					.responseMessage(AccountUtils.Account_Not_Exist_Message)
					.accountInfo(null)
					.build();
		}
		User userToCredit = userRepository.findByAccountNumber(request.getAccountNumber());
		userToCredit.setAccountBalance(userToCredit.getAccountBalance().add(request.getAmount()));
		User creditUser=userRepository.save(userToCredit);
		
		//Save Transaction
		TransactionDto transactionDto= TransactionDto.builder()
				.accountNumber(userToCredit.getAccountNumber())
				.transactionType("CREDIT")
				.amount(request.getAmount())
				.build()	;	
		transactionService.saveTransaction(transactionDto);
		
		EmailDetails emailDetails=EmailDetails.builder()
				.recipient(creditUser.getEmail())
				.subject("Account Transaction: Credited")
				.messageBody("Dear "+creditUser.getFirstName()+" "+creditUser.getLastName()+"!! \n We wish to inform you that your account xxxxxx"+
						creditUser.getAccountNumber().substring(6)+" is credited of "+request.getAmount()+"\n Available Balance: "+creditUser.getAccountBalance())
				
				.build();
		
		emailService.sendEmailAlert(emailDetails);
		
		return BankResponse.builder()
				.responseCode(AccountUtils.Account_Credit_Success_Code)
				.responseMessage(AccountUtils.Account_Credit_Success_Message)
				.accountInfo(AccountInfo.builder()
						.accountName(userToCredit.getFirstName()+" "+userToCredit.getLastName())
						.accountNumber(userToCredit.getAccountNumber())
						.accountBalance(userToCredit.getAccountBalance())
						.build())
				.build();


	}

	@Override
	public BankResponse debitAccount(CreditDebitRequest request) {
		// Check if Account Exist
		
		Boolean isAccountExist = userRepository.existsByAccountNumber(request.getAccountNumber());
		if(!isAccountExist) {
			return BankResponse.builder()
					.responseCode(AccountUtils.Account_Not_Exist_Code)
					.responseMessage(AccountUtils.Account_Not_Exist_Message)
					.accountInfo(null)
					.build();
		}
		
		User userToDebit=userRepository.findByAccountNumber(request.getAccountNumber());
		
		//Check the withdraw amount is not less than the balance
		BigInteger availableBalance=userToDebit.getAccountBalance().toBigInteger();
		BigInteger debitAmount=request.getAmount().toBigInteger();
		if(availableBalance.intValue() < debitAmount.intValue()) {
			return BankResponse.builder()
					.responseCode(AccountUtils.Insufficient_Balance_Code)
					.responseMessage(AccountUtils.Insufficient_Balance_Message)
					.accountInfo(null)
					.build();
			
		}else {
			
			userToDebit.setAccountBalance(userToDebit.getAccountBalance().subtract(request.getAmount()));
			User debitUser=userRepository.save(userToDebit);
			
			//Save Transaction
			TransactionDto transactionDto= TransactionDto.builder()
					.accountNumber(userToDebit.getAccountNumber())
					.transactionType("DEBIT")
					.amount(request.getAmount())
					.build();	
			transactionService.saveTransaction(transactionDto);
			
			EmailDetails emailDetails=EmailDetails.builder()
					.recipient(debitUser.getEmail())
					.subject("Account Transaction: Debited")
					.messageBody("Dear "+debitUser.getFirstName()+" "+debitUser.getLastName()+"!! \n We wish to inform you that your account xxxxxx"+
							debitUser.getAccountNumber().substring(6)+" is debited of "+request.getAmount()+"\n Available Balance: "+debitUser.getAccountBalance())
					
					.build();
			
			emailService.sendEmailAlert(emailDetails);

			
			
			return BankResponse.builder()
					.responseCode(AccountUtils.Account_Debit_Success_Code)
					.responseMessage(AccountUtils.Account_Debit_Success_Message)
					.accountInfo(AccountInfo.builder()
							.accountName(userToDebit.getFirstName()+" "+userToDebit.getLastName())
							.accountNumber(userToDebit.getAccountNumber())
							.accountBalance(userToDebit.getAccountBalance())
							.build())
					.build();

			
		}

	}

	@Override
	public BankResponse transfer(TransferRequest request) {
		// Check Destination Account Exist
		Boolean isDestinationAccountExist = userRepository.existsByAccountNumber(request.getDestinationAccountNumber());
		if(!isDestinationAccountExist) {
			return BankResponse.builder()
					.responseCode(AccountUtils.Account_Not_Exist_Code)
					.responseMessage(AccountUtils.Account_Not_Exist_Message)
					.accountInfo(null)
					.build();
		}
		
		// get the account to be debited (account exist)
		User sourceAccountUser=userRepository.findByAccountNumber(request.getSourceAccountNumber());
		
		//check the debited amount not less than the balance

		if(request.getAmount().compareTo(sourceAccountUser.getAccountBalance()) > 0) {
			
			return BankResponse.builder()
					.responseCode(AccountUtils.Insufficient_Balance_Code)
					.responseMessage(AccountUtils.Insufficient_Balance_Message)
					.accountInfo(null)
					.build();
			
			
		}
		
		//debit the account
		sourceAccountUser.setAccountBalance(sourceAccountUser.getAccountBalance().subtract(request.getAmount()));
		User sourceDebitUser=userRepository.save(sourceAccountUser);
		
		EmailDetails debitEmail=EmailDetails.builder()
				.recipient(sourceDebitUser.getEmail())
				.subject("Account Alert: Debited")
				.messageBody("Dear "+sourceDebitUser.getFirstName()+" "+sourceDebitUser.getLastName()+"!! \n We wish to inform you that your account xxxxxx"+
						sourceDebitUser.getAccountNumber().substring(6)+" is debited of "+request.getAmount()+"\n Available Balance: "+sourceDebitUser.getAccountBalance())
				
				.build();
		
		emailService.sendEmailAlert(debitEmail);

		
		//get the account to be credited
		User destinationAccountUser=userRepository.findByAccountNumber(request.getDestinationAccountNumber());
		//credit the account
		destinationAccountUser.setAccountBalance(destinationAccountUser.getAccountBalance().add(request.getAmount()));
		User destinationCreditUser=userRepository.save(destinationAccountUser);
		
		TransactionDto transactionDto= TransactionDto.builder()
				.accountNumber(destinationCreditUser.getAccountNumber())
				.transactionType("CREDIT")
				.amount(request.getAmount())
				.build();	
		transactionService.saveTransaction(transactionDto);
		
		EmailDetails creditEmail=EmailDetails.builder()
				.recipient(destinationCreditUser.getEmail())
				.subject("Account Alert: Credit")
				.messageBody("Dear "+destinationCreditUser.getFirstName()+" "+destinationCreditUser.getLastName()+"!! \n We wish to inform you that your account xxxxxx"+
						destinationCreditUser.getAccountNumber().substring(6)+" is credut of "+request.getAmount()+"\n Available Balance: "+destinationCreditUser.getAccountBalance()+
						"\n Transaction Details: \n Sender Name: "+sourceDebitUser.getFirstName()+" "+sourceDebitUser.getLastName()+"\n Sender Account Number: "+
						sourceDebitUser.getAccountNumber().substring(6))
				
				.build();
		
		emailService.sendEmailAlert(creditEmail);
		
		return BankResponse.builder()
				.responseCode(AccountUtils.Account_Transfer_Code)
				.responseMessage(AccountUtils.Account_Transfer_Message)
				.accountInfo(null)
				.build();


	}





	

		

}
