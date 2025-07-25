package com.bank.mybank.config;

import static org.mockito.Mockito.mock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;


@Configuration
@Profile("test")  
public class TestMailConfig {
	
    @Bean
    public JavaMailSender javaMailSender() {
        return mock(JavaMailSender.class);  // Returns a mock bean
    }


}
