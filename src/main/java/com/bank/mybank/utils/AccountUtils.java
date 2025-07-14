package com.bank.mybank.utils;

import java.time.Year;

public class AccountUtils {
	
	public static final String Account_Exist_Code="001";
	public static final String Account_Exist_Message="This Account already Exist";
	
	public static final String Account_Created_Success_Code="002";
	public static final String Account_Created_Success_Message="Successfully Created Your Account";
	
	
	public static final String Account_Not_Exist_Code="003";
	public static final String Account_Not_Exist_Message="This Account Does Not Exist";
	
	public static final String Account_Found_Code="004";
	public static final String Account_Found_Message="User Account Found";
	
	public static final String Account_Credit_Success_Code="005";
	public static final String Account_Credit_Success_Message="Successfully Credit";
	
	public static final String Insufficient_Balance_Code="006";
	public static final String Insufficient_Balance_Message="Insufficient Balance";
	
	public static final String Account_Debit_Success_Code="007";
	public static final String Account_Debit_Success_Message="Successfully Debited";
	
	public static final String Account_Transfer_Code="008";
	public static final String Account_Transfer_Message="Transfer Successful";

	
	
	
	




	
		
	
	
	public static String generateAccountNumber() {
		// 2025 + randomSixDigit Number
		
		Year currentYear= Year.now();
		
		int min=100000;
		
		int max=999999;
		
		//Generate Random Number
		
		int randNumber= (int) Math.floor(Math.random()*(max-min+1)+min);
		
		//Convert randNumber and current Year to String and concatenate
		
		String year =String.valueOf(currentYear);
		
		String randomNumber= String.valueOf(randNumber);
		
		StringBuilder accountNumber = new StringBuilder();

		
		return accountNumber.append(year).append(randomNumber).toString();
				
		
		
	}
	
	

}
