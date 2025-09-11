package com.example.atm.BankingAtm.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="transactions")
public class Transaction {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="account_id")
    private Account account;
    private String type;
    private BigDecimal amount;
    private LocalDateTime timestamp = LocalDateTime.now();
    private String remarks;
}
