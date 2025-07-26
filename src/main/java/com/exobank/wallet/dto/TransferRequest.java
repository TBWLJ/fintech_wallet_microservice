package com.exobank.wallet.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferRequest {
    private String senderUserId;
    private String receiverUserId;
    private BigDecimal amount;
    private String reference;    // unique transaction reference
    private String narration;    // optional description
}
