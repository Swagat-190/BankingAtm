package com.example.atm.BankingAtm.controller;

import com.example.atm.BankingAtm.entity.Account;
import com.example.atm.BankingAtm.entity.Transaction;
import com.example.atm.BankingAtm.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
@Tag(name = "Account Management", description = "APIs for managing accounts, deposits, withdrawals, transfers, and transactions")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Operation(
            summary = "Create a new account",
            description = "Creates a new account for a user with the given email and initial balance"
    )
    @PostMapping("/create")
    public Account createAccount(@RequestParam String email, @RequestParam Double balance) {
        return accountService.createAccount(email, balance);
    }

    @Operation(
            summary = "Deposit money",
            description = "Deposits the specified amount into the user’s account identified by email"
    )
    @PostMapping("/deposit")
    public Account deposit(@RequestParam String email, @RequestParam Double amount) {
        return accountService.deposit(email, amount);
    }

    @Operation(
            summary = "Withdraw money",
            description = "Withdraws the specified amount from the user’s account identified by email"
    )
    @PostMapping("/withdraw")
    public Account withdraw(@RequestParam String email, @RequestParam Double amount) {
        return accountService.withdraw(email, amount);
    }

    @Operation(
            summary = "Check account balance",
            description = "Returns the current balance of the logged-in user"
    )
    @GetMapping("/balance")
    public ResponseEntity<Map<String, Object>> getBalance(Authentication authentication) {
        String email = authentication.getName();
        Account account = accountService.getAccountByEmail(email);

        Map<String, Object> response = new HashMap<>();
        response.put("balance", account.getBalance());
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Get transaction history",
            description = "Returns all transactions for a given user email"
    )
    @GetMapping("/transactions")
    public List<Transaction> getTransactions(@RequestParam String email) {
        return accountService.getTransactions(email);
    }

    @Operation(
            summary = "Transfer money",
            description = "Transfers a specified amount from one user account to another"
    )
    @PostMapping("/transfer")
    public boolean transferMoney(
            @RequestParam String fromEmail,
            @RequestParam String toEmail,
            @RequestParam BigDecimal amount) {
        return accountService.transfer(fromEmail, toEmail, amount);
    }
}
