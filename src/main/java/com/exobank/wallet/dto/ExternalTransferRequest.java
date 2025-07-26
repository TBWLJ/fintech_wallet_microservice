package com.exobank.wallet.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExternalTransferRequest {

    @NotBlank(message = "User ID is required")
    private String userId;

    @NotBlank(message = "Bank code is required")
    private String bankCode; // NIBSS bank code

    @NotBlank(message = "Account number is required")
    private String accountNumber;

    @NotBlank(message = "Account name is required")
    private String accountName;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
    private BigDecimal amount;

    private String narration;
}
