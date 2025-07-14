package com.bank.mybank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@OpenAPIDefinition(
		info=@Info(
				title="My Java Bank",
				description="Bckend REST API's for My Java Bank",
				version="v1.0",
				contact= @Contact(
						name="Faham Khan",
						email="fahamkhan11@gmail.com",
						url="https://www.linkedin.com/in/faham0/"
						),
				license=@License(
						name="My Java Bank",
						url="https://github.com/fahamkhan00/MyJavaBank"				
				)
		
		),
externalDocs= @ExternalDocumentation(
		description="My Java Bank App Documentation",
		url="https://github.com/fahamkhan00/MyJavaBank"
		)
)
public class MyBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyBankApplication.class, args);
	}

}
