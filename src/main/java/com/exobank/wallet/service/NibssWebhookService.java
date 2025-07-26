package com.exobank.wallet.service;

import com.exobank.wallet.dto.NibssWebhookPayload;
import com.exobank.wallet.model.Transaction;
import com.exobank.wallet.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class NibssWebhookService {

    private final TransactionRepository transactionRepository;

    public void processWebhook(NibssWebhookPayload payload) {
        log.info("Received NIBSS webhook: {}", payload);

        Optional<Transaction> txnOpt = transactionRepository.findByReference(payload.getReference());

        txnOpt.ifPresent(txn -> {
            txn.setStatus(payload.getStatus().toUpperCase());
            txn.setDescription(payload.getReason());
            transactionRepository.save(txn);

            log.info("Updated transaction {} with status {}", payload.getReference(), payload.getStatus());
        });
    }
}
