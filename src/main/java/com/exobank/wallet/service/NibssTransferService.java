package com.exobank.wallet.service;

import com.exobank.wallet.dto.NameEnquiryRequest;
import com.exobank.wallet.dto.NameEnquiryResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class NibssTransferService {

    private final RestTemplate restTemplate;

    private final String NAME_ENQUIRY_URL = "https://nibss-api.ng/name-enquiry";

    public NameEnquiryResponse performNameEnquiry(NameEnquiryRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // headers.set("Authorization", "Bearer " + <NIBSS-TOKEN>); // if required

        HttpEntity<NameEnquiryRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<NameEnquiryResponse> response = restTemplate.exchange(
                NAME_ENQUIRY_URL,
                HttpMethod.POST,
                entity,
                NameEnquiryResponse.class
        );

        return response.getBody();
    }
}
