package com.market.purchase.integration.impl;

import com.market.purchase.integration.ProductApiFacade;
import com.market.purchase.model.ProductLock;
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
    public ResponseEntity<ProductLock> lockProduct(ProductLock productLock) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);

        HttpEntity<ProductLock> request = new HttpEntity<>(productLock, headers);
        return restTemplate.postForEntity(this.productsApiHost.concat("/lock"), request, ProductLock.class);
    }

    @Override
    public void deleteLock(String lockId) {
        restTemplate.delete(this.productsApiHost.concat("/" + lockId + "/lock"));
    }
}
