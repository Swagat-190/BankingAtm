package com.example.atm.BankingAtm.repository;
import com.example.atm.BankingAtm.entity.Account;
import com.example.atm.BankingAtm.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccount(Account account);
}
