🏦 MyBank - Spring Boot JWT Authentication API

A secure banking backend application built with Spring Boot, JWT authentication, MySQL, and Docker. This project implements user registration, login, role-based access control, and secure API endpoints.

## 🚀 Features

- JWT-based authentication and authorization
- Role-based access control
- RESTful API design
- Secure password hashing with BCrypt
- Custom authentication filter
- Global exception handling
- Integrated with MySQL database
- Dockerized for containerized deployment

## 📦 Tech Stack

- **Backend**: Java, Spring Boot, Spring Security
- **Security**: JWT, BCrypt
- **Database**: MySQL
- **Tools**: Docker, Maven, Lombok

## 📁 Project Structure
src

├── config # Security config, filters, JWT utilities

├── controller # API endpoints

├── dto # Data Transfer Objects

├── entity # JPA entities

├── exception # Custom exceptions and handlers

├── repository # Spring Data JPA repositories

├── service # Business logic

└── MyBankApplication.java


## ⚙️ Configuration

Make sure to update the values in `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/mybankdb
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password

app.jwt-secret=your_jwt_secret
app.jwt-expiration=604800000  # 7 days in milliseconds

pring.mail.host=smtp.gmail.com

spring.mail.port= 587

spring.mail.username=your_mail

spring.mail.password=app_pass


spring.mail.properties.mail.smtp.auth=true

spring.mail.properties.mail.smtp.starttls.enable=true
```

🔐 API Endpoints

Method	Endpoint	Access	Description

POST	/api/user	Public	Register a new user

POST	/api/user/login	Public	Authenticate and get JWT

POST	/api/user/credit	Credit to selected User

POST	/api/user/Debit	Debit to selected User

POST	/api/user/transfer	Transfer Amount to any user

GET	/bankStatement	Get user transactions Statement

GET	/api/user/balanceEnquiry	Check account balance

GET	/api/user/nameEnquiry	Check Account Holder name


<img width="1920" height="1048" alt="createUser" src="https://github.com/user-attachments/assets/eed39d78-13d0-4ff7-8e84-3833665882b0" />
<img width="1920" height="1048" alt="credit" src="https://github.com/user-attachments/assets/0f94e9b7-7405-46a1-8a43-adab4e98ff41" />
<img width="1920" height="1048" alt="login" src="https://github.com/user-attachments/assets/d6525feb-a134-4fd7-abb9-28a203ccca5a" />
<img width="1920" height="1048" alt="balanceEnquiry" src="https://github.com/user-attachments/assets/095fc7da-461e-48be-ae0f-db49f0a9d1f3" />
<img width="1920" height="1048" alt="code" src="https://github.com/user-attachments/assets/eed17b2e-d46f-4ec4-ac07-211c8a764e9d" />

✅ Prerequisites
  * Java 17+

  * MySQL Server

  * Maven

  * Docker (optional, for containerization)

📝 Author
Developed by Faham Khan


---

Let me know if you'd like:
- 🧪 Postman collection file
- 🌐 Swagger documentation
- 🌱 Version without Docker

Just say the word!







