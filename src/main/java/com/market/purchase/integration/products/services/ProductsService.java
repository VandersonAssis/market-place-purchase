package com.market.purchase.integration.products.services;

import com.market.purchase.model.ProductLock;
import org.springframework.http.ResponseEntity;

public interface ProductsService {
    ResponseEntity<ProductLock> lockProductQuantity(ProductLock productLock);
    ResponseEntity<Void> deleteLock(String idLock);
}
