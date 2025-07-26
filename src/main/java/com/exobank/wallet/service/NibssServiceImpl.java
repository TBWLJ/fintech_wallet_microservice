package com.exobank.wallet.service;

import com.exobank.wallet.config.NibssConfig;
import com.exobank.wallet.integration.NibssService;
import com.exobank.wallet.dto.NibssTransferRequest;
import com.exobank.wallet.dto.NibssTransferResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import org.springframework.web.client.RestClientException;

@Service
@RequiredArgsConstructor
public class NibssServiceImpl implements NibssService {

    private final NibssConfig nibssConfig;
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public boolean transferToBank(String bankCode, String accountNumber, String accountName, BigDecimal amount, String reference, String narration) {
        NibssTransferRequest request = new NibssTransferRequest();
        request.setClientId(nibssConfig.getClientId());
        request.setBankCode(bankCode);
        request.setAccountNumber(accountNumber);
        request.setAccountName(accountName);
        request.setAmount(amount);
        request.setReference(reference);
        request.setNarration(narration);
        request.setCallbackUrl(nibssConfig.getCallbackUrl());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-API-KEY", nibssConfig.getApiKey());

        HttpEntity<NibssTransferRequest> entity = new HttpEntity<>(request, headers);

        try {
            ResponseEntity<NibssTransferResponse> response = restTemplate.exchange(
                nibssConfig.getBaseUrl() + "/transfer",
                HttpMethod.POST,
                entity,
                NibssTransferResponse.class
            );

            NibssTransferResponse body = response.getBody();
            return body != null && body.isSuccess();

        } catch (RestClientException e) {
            e.printStackTrace();
            return false;
        }
    }
}
