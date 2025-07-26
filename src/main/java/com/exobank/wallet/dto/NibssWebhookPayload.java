package com.exobank.wallet.dto;

import lombok.Data;

@Data
public class NibssWebhookPayload {
    private String reference;
    private String status;
    private String reason;
    private String bankCode;
    private String accountNumber;
    private String transactionDate;
}
