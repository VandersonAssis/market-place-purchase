package com.market.purchase.config;

import com.market.exceptions.exceptionhandlers.ClientExceptionHandlers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new ClientExceptionHandlers());
        return restTemplate;
    }

}