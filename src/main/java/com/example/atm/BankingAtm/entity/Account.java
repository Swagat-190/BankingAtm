package com.example.atm.BankingAtm.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="accounts")
public class Account {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name="user_id")
    private User user;
    private BigDecimal balance = BigDecimal.ZERO;
    private String accountType = "SAVINGS";
    private LocalDateTime createdAt = LocalDateTime.now();


}

