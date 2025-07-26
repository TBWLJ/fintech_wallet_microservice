package com.exobank.wallet.dto;

import lombok.Data;

@Data
public class NibssTransferResponse {
    private boolean success;
    private String message;
    private String transactionReference;
    private String responseCode;
}
