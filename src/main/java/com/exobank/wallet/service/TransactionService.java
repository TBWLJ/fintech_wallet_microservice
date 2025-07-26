package com.exobank.wallet.service;

import com.exobank.wallet.model.Transaction;
import com.exobank.wallet.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactionsByWalletId(String walletId) {
        return transactionRepository.findByWalletIdOrderByCreatedAtDesc(walletId);
    }

    public boolean referenceExists(String reference) {
        return transactionRepository.existsByReference(reference);
    }
}
