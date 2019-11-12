package com.market.purchase.integration.impl;

import com.market.purchase.integration.ProductApiFacade;
import com.market.purchase.model.PurchaseProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class ProductApiFacadeImpl implements ProductApiFacade {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${integration.api.products.host}")
    private String productsApiHost;

    @Override
    public ResponseEntity<String> lockProduct(PurchaseProduct purchaseProduct) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);

        HttpEntity<PurchaseProduct> request = new HttpEntity<>(purchaseProduct, headers);
        return restTemplate.postForEntity(this.productsApiHost.concat("/lock"), request, String.class);
    }
}
