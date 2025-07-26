package com.exobank.wallet.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "nibss")
public class NibssConfig {
    private String baseUrl;
    private String clientId;
    private String apiKey;
    private String apiSecret;
    private String callbackUrl;
}
