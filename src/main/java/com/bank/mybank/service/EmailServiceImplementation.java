package com.bank.mybank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.bank.mybank.dto.EmailDetails;

import org.springframework.beans.factory.annotation.Value;
//import lombok.Value;

@Service  
public class EmailServiceImplementation implements EmailService {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	
	@Value("${spring.mail.username}")
	private String senderEmail;


	@Override
	public void sendEmailAlert(EmailDetails emailDetails) {
		try {
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setFrom(senderEmail);
			mailMessage.setTo(emailDetails.getRecipient());
			mailMessage.setText(emailDetails.getMessageBody());
			mailMessage.setSubject(emailDetails.getSubject());
			
			javaMailSender.send(mailMessage);
			
			System.out.println("MAIL SENT!!!");
			
		}catch(MailException e) {
			throw new RuntimeException(e);
		}
	}
	
	

}
