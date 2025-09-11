package com.example.atm.BankingAtm.service;
import com.example.atm.BankingAtm.entity.Account;
import com.example.atm.BankingAtm.entity.User;
import com.example.atm.BankingAtm.repository.AccountRepository;
import com.example.atm.BankingAtm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Optional;
import com.example.atm.BankingAtm.entity.Transaction;
import com.example.atm.BankingAtm.repository.TransactionRepository;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    // Create a new account
    @Transactional
    public Account createAccount(String email, Double initialBalance) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) throw new RuntimeException("User not found");
        User user = userOpt.get();

        Account account = new Account();
        account.setUser(user);
        account.setBalance(BigDecimal.valueOf(initialBalance));
        account.setCreatedAt(LocalDateTime.now());
        Account savedAccount = accountRepository.save(account);

        // Add initial deposit transaction
        Transaction t = new Transaction();
        t.setAccount(savedAccount);
        t.setType("DEPOSIT");
        t.setAmount(BigDecimal.valueOf(initialBalance));
        t.setTimestamp(LocalDateTime.now());
        transactionRepository.save(t);

        return savedAccount;
    }

    // Deposit money
    @Transactional
    public Account deposit(String email, Double amount) {
        Account account = getAccountByEmail(email);
        account.setBalance(account.getBalance().add(BigDecimal.valueOf(amount)));

        Transaction t = new Transaction();
        t.setAccount(account);
        t.setType("DEPOSIT");
        t.setAmount(BigDecimal.valueOf(amount));
        t.setTimestamp(LocalDateTime.now());
        transactionRepository.save(t);

        return accountRepository.save(account);
    }

    // Withdraw money
    @Transactional
    public Account withdraw(String email, Double amount) {
        Account account = getAccountByEmail(email);
        if (account.getBalance().compareTo(BigDecimal.valueOf(amount)) < 0)
            throw new RuntimeException("Insufficient balance");

        account.setBalance(account.getBalance().subtract(BigDecimal.valueOf(amount)));

        Transaction t = new Transaction();
        t.setAccount(account);
        t.setType("WITHDRAW");
        t.setAmount(BigDecimal.valueOf(amount));
        t.setTimestamp(LocalDateTime.now());
        transactionRepository.save(t);

        return accountRepository.save(account);
    }

    // Check balance
    public BigDecimal getBalance(String email) {
        Account account = getAccountByEmail(email);
        return account.getBalance();
    }

    // Get transaction history
    public List<Transaction> getTransactions(String email) {
        Account account = getAccountByEmail(email);
        return transactionRepository.findByAccount(account);
    }

    // Helper method
    public Account getAccountByEmail(String email) {
        Optional<Account> accountOpt = accountRepository.findByUserEmail(email);
        if (accountOpt.isEmpty()) throw new RuntimeException("Account not found");
        return accountOpt.get();
    }

    @Transactional
        public boolean transfer(String fromEmail, String toEmail, BigDecimal amount) {
            if (fromEmail.equals(toEmail)) {
                throw new IllegalArgumentException("Cannot transfer to the same account");
            }

            // Fetch sender
            User senderUser = userRepository.findByEmail(fromEmail)
                    .orElseThrow(() -> new RuntimeException("Sender not found with email: " + fromEmail));
            Account senderAccount = accountRepository.findByUser(senderUser)
                    .orElseThrow(() -> new RuntimeException("Sender account not found"));

            // Fetch receiver
            User receiverUser = userRepository.findByEmail(toEmail)
                    .orElseThrow(() -> new RuntimeException("Receiver not found with email: " + toEmail));
            Account receiverAccount = accountRepository.findByUser(receiverUser)
                    .orElseThrow(() -> new RuntimeException("Receiver account not found"));

            // Check balance
            if (senderAccount.getBalance().compareTo(amount) < 0) {
                throw new RuntimeException("Insufficient balance in sender's account");
            }

            // Perform transfer
            senderAccount.setBalance(senderAccount.getBalance().subtract(amount));
            receiverAccount.setBalance(receiverAccount.getBalance().add(amount));

            // Save updates
            accountRepository.save(senderAccount);
            accountRepository.save(receiverAccount);
            return true;
        }


}

