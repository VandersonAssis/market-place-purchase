package com.market.purchase.integration;

import com.market.purchase.model.PurchaseProduct;
import org.springframework.http.ResponseEntity;

public interface ProductApiFacade {
    ResponseEntity<String> lockProduct(PurchaseProduct purchaseProduct);
}
