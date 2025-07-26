package com.exobank.wallet.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WithdrawRequest {
    private String userId; // wallet owner
    private BigDecimal amount;
    private String bankCode;        // e.g. "058" for GTBank
    private String accountNumber;   // e.g. "0123456789"
    private String reference;       // unique reference string
    private String narration;       // optional description
}
