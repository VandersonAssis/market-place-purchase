package com.market.purchase.integration;

import com.market.purchase.model.ProductLock;
import org.springframework.http.ResponseEntity;

public interface ProductApiFacade {
    ResponseEntity<ProductLock> lockProduct(ProductLock productLock);
    void deleteLock(String lockId);
}
