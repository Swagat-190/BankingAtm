package com.example.atm.BankingAtm.controller;
import com.example.atm.BankingAtm.entity.Account;
import com.example.atm.BankingAtm.entity.Transaction;
import com.example.atm.BankingAtm.service.AccountService;
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
public class AccountController {

    @Autowired
    private AccountService accountService;

    // Create a new account
    @PostMapping("/create")
    public Account createAccount(@RequestParam String email, @RequestParam Double balance) {
        return accountService.createAccount(email, balance);
    }

    // Deposit money
    @PostMapping("/deposit")
    public Account deposit(@RequestParam String email, @RequestParam Double amount) {
        return accountService.deposit(email, amount);
    }

    // Withdraw money
    @PostMapping("/withdraw")
    public Account withdraw(@RequestParam String email, @RequestParam Double amount) {
        return accountService.withdraw(email, amount);
    }

    // Check balance
    @GetMapping("/balance")
    public ResponseEntity<Map<String, Object>> getBalance(Authentication authentication) {
        // The Authentication object comes from the JWT filter
        String email = authentication.getName(); // email of logged-in user
        Account account = accountService.getAccountByEmail(email);

        Map<String, Object> response = new HashMap<>();
        response.put("balance", account.getBalance());
        return ResponseEntity.ok(response);
    }


    // Transaction history
    @GetMapping("/transactions")
    public List<Transaction> getTransactions(@RequestParam String email) {
        return accountService.getTransactions(email);
    }

    @PostMapping("/transfer")
    public boolean transferMoney(@RequestParam String fromEmail , String toEmail , BigDecimal amount){
        return  accountService.transfer(fromEmail, toEmail, amount);
    }
}
