package com.exobank.wallet.service;

import com.exobank.wallet.dto.ExternalTransferRequest;
import com.exobank.wallet.dto.TransferRequest;
import com.exobank.wallet.dto.WithdrawRequest;
import com.exobank.wallet.integration.NibssService;
import com.exobank.wallet.model.Transaction;
import com.exobank.wallet.model.TransactionType;
import com.exobank.wallet.model.Wallet;
import com.exobank.wallet.repository.WalletRepository;
import com.exobank.wallet.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private NibssService nibssService;


    public Wallet createWallet(String userId) {
        if (walletRepository.existsByUserId(userId)) {
            throw new RuntimeException("Wallet already exists for user");
        }

        Wallet wallet = new Wallet();
        wallet.setUserId(userId);
        wallet.setBalance(BigDecimal.ZERO);
        return walletRepository.save(wallet);
    }

    public Wallet getWalletByUserId(String userId) {
        return walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));
    }

    public Wallet creditWallet(String userId, BigDecimal amount) {
        Wallet wallet = getWalletByUserId(userId);
        wallet.setBalance(wallet.getBalance().add(amount));
        return walletRepository.save(wallet);
    }

    public Wallet debitWallet(String userId, BigDecimal amount) {
        Wallet wallet = getWalletByUserId(userId);
        if (wallet.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }
        wallet.setBalance(wallet.getBalance().subtract(amount));
        return walletRepository.save(wallet);
    }

    @Transactional
    public void transfer(TransferRequest request) {
        if (request.getSenderUserId().equals(request.getReceiverUserId())) {
            throw new IllegalArgumentException("Cannot transfer to self");
        }

        Wallet sender = getWalletByUserId(request.getSenderUserId());
        Wallet receiver = getWalletByUserId(request.getReceiverUserId());

        if (sender.getBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        // Debit sender
        sender.setBalance(sender.getBalance().subtract(request.getAmount()));
        walletRepository.save(sender);

        // Credit receiver
        receiver.setBalance(receiver.getBalance().add(request.getAmount()));
        walletRepository.save(receiver);

        // Log transaction: sender
        transactionRepository.save(new Transaction(
                null, // id (let DB generate)
                sender.getId(),
                request.getReceiverUserId(),
                request.getAmount(),
                TransactionType.TRANSFER.name(),
                request.getReference(),
                "Transfer to user " + request.getReceiverUserId(),
                java.time.LocalDateTime.now()
        ));

        // Log transaction: receiver
        transactionRepository.save(new Transaction(
                null, // id (let DB generate)
                receiver.getId(),
                request.getSenderUserId(),
                request.getAmount(),
                TransactionType.TRANSFER.name(),
                request.getReference(),
                "Received from user " + request.getSenderUserId(),
                java.time.LocalDateTime.now()
        ));
    }

    @Transactional
    public void withdraw(WithdrawRequest request) {
        Wallet wallet = getWalletByUserId(request.getUserId());

        if (wallet.getBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Insufficient balance for withdrawal");
        }

        // Deduct balance
        wallet.setBalance(wallet.getBalance().subtract(request.getAmount()));
        walletRepository.save(wallet);

        // Save transaction
        transactionRepository.save(new Transaction(
                null, // id (let DB generate)
                wallet.getId(),
                null, // receiverUserId (not applicable for withdrawal)
                request.getAmount(),
                TransactionType.WITHDRAWAL.name(),
                request.getReference(),
                "Withdraw to bank: " + request.getBankCode() + " - " + request.getAccountNumber(),
                java.time.LocalDateTime.now()
        ));

        // TODO: integrate with external bank API (e.g., Flutterwave, Mono, Paystack)
    }

    public void transferToExternalBank(ExternalTransferRequest request) {
        Wallet wallet = getWalletByUserId(request.getUserId());

        if (wallet.getBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        // Generate reference
        String reference = UUID.randomUUID().toString();

        // Debit user
        wallet.setBalance(wallet.getBalance().subtract(request.getAmount()));
        walletRepository.save(wallet);

        // Save transaction
        Transaction txn = Transaction.builder()
                .walletId(wallet.getId())
                .amount(request.getAmount())
                .reference(reference)
                .description(request.getNarration())
                .type(TransactionType.EXTERNAL_TRANSFER)
                .build();

        transactionRepository.save(txn);

        // Initiate transfer with NIBSS
        boolean success = nibssService.transferToBank(
            request.getBankCode(),
            request.getAccountNumber(),
            request.getAccountName(),
            request.getAmount(),
            txn.getReference(),
            request.getNarration()
        );

        if (!success) {
            wallet.setBalance(wallet.getBalance().add(request.getAmount()));
            walletRepository.save(wallet);
            throw new RuntimeException("External bank transfer failed");
        }
    }
}
