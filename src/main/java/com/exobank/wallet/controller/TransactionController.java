package com.exobank.wallet.controller;

import com.exobank.wallet.model.Transaction;
import com.exobank.wallet.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    // 1. Get all transactions
    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }

    // 2. Get a transaction by ID
    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable String id) {
        return ResponseEntity.ok(transactionService.getTransactionById(id));
    }

    // 3. Get transactions by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Transaction>> getTransactionsByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(transactionService.getTransactionsByUserId(userId));
    }

    // 4. Optional: Add transaction manually
    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        return ResponseEntity.ok(transactionService.saveTransaction(transaction));
    }
}
