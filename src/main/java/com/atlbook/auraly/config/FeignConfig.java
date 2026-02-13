package com.atlbook.auraly.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor basicAuthRequestInterceptor() {
        return requestTemplate -> {
            String auth = "650150:f2b3201a8ff7182b5b27ec87cea80d3fd9b956c3";
            String encodedAuth = Base64.getEncoder()
                    .encodeToString(auth.getBytes(StandardCharsets.UTF_8));

            requestTemplate.header("Authorization", "Basic " + encodedAuth);
            requestTemplate.header("Content-Type", "application/json");
        };
    }
}
