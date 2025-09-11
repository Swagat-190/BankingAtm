# BankingATM System

A full-featured **Banking ATM System** built with **Spring Boot** for the backend.  
Supports user registration, login, balance check, deposit, withdrawal, and fund transfer with JWT-based authentication.  

---

## Table of Contents
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Setup Instructions](#setup-instructions)
- [API Endpoints](#api-endpoints)
- [Postman Collection](#postman-collection)
- [Future Enhancements](#future-enhancements)
- [Author](#author)

---

## Features
- User registration and login with secure password hashing
- JWT-based authentication for API security
- Account management:
  - Check balance
  - Deposit money
  - Withdraw money
  - Transfer money between accounts
- Transaction history tracking
- CORS-enabled for frontend integration

---

## Technologies Used
- Java 17
- Spring Boot 3
- Spring Security with JWT
- Hibernate / JPA
- MySQL
- Postman (for API testing)
- Maven

---

## Setup Instructions

1. **Clone the repository**
```bash
git clone https://github.com/Swagat-190/BankingAtm.git
cd BankingAtm

Setup MySQL database
CREATE DATABASE BankAtm;
 Api endpoints
| Method | Endpoint             | Description         | Body                                                                             |
| ------ | -------------------- | ------------------- | -------------------------------------------------------------------------------- |
| POST   | `/api/auth/register` | Register a new user | `{ "email": "user@example.com", "username": "user", "password": "password123" }` |
| POST   | `/api/auth/login`    | Login user          | `{ "email": "user@example.com", "password": "password123" }`                     |

