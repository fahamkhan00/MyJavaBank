package com.bank.mybank.service;

import com.bank.mybank.dto.EmailDetails;

public interface EmailService {
	
	void sendEmailAlert(EmailDetails emailDetails);

}
