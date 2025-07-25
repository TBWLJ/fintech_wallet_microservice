package com.exobank.wallet.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String walletId;

    @Column(nullable = false)
    private String type; // e.g., "CREDIT", "DEBIT", "TRANSFER"

    @Column(nullable = false)
    private BigDecimal amount;

    private String description;

    @Column(nullable = false)
    @Builder.Default
    private String status = "PENDING"; // e.g., "PENDING", "SUCCESS", "FAILED"

    private String reference;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}
