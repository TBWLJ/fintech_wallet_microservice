package com.exobank.wallet.repository;

import com.exobank.wallet.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
    List<Transaction> findByWalletId(String walletId);
    List<Transaction> findByWalletIdOrderByCreatedAtDesc(String walletId);
    boolean existsByReference(String reference);
}
