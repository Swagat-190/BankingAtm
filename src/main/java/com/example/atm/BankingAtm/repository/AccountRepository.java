package com.example.atm.BankingAtm.repository;
import com.example.atm.BankingAtm.entity.Account;
import com.example.atm.BankingAtm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUser(User user);

    Optional<Account> findByUserEmail(String email);
}
