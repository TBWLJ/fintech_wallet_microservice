package com.exobank.wallet.dto;

import lombok.Data;

@Data
public class NameEnquiryResponse {
    private String accountName;
    private String accountNumber;
    private String bankCode;
    private String sessionId;
    private String responseCode;
    private String responseMessage;
}
