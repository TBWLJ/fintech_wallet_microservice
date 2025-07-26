// NibssTransferRequest.java
package com.exobank.wallet.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class NibssTransferRequest {
    private String clientId;
    private String accountNumber;
    private String bankCode;
    private String accountName;
    private BigDecimal amount;
    private String reference;
    private String narration;
    private String callbackUrl;
}
