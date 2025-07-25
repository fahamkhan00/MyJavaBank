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
public class UserRequest {
	
	private Long id;
	private String firstName;
	private String lastName;
	private String otherName;
	private String gender;
	private String address;
	private String stateofOrigin;
	
	private String email;
	
	private String password;
	
	private String phoneNumber;
	
	private String alternativePhoneNumber;
	
	private String Status;

}
