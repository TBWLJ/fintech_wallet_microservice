package com.exobank.wallet.mapper;

import com.exobank.wallet.dto.TransactionResponse;
import com.exobank.wallet.model.Transaction;
import com.exobank.wallet.model.TransactionType;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public TransactionResponse toDto(Transaction txn) {
        TransactionResponse dto = new TransactionResponse();
        dto.setId(txn.getId());
        dto.setWalletId(txn.getWalletId());
        dto.setAmount(txn.getAmount());
        dto.setType(TransactionType.valueOf(txn.getType()));
        dto.setReference(txn.getReference());
        dto.setDescription(txn.getDescription());
        dto.setCreatedAt(txn.getCreatedAt());
        return dto;
    }
}
