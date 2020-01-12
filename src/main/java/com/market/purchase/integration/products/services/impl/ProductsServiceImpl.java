package com.market.purchase.integration.products.services.impl;

import com.market.purchase.integration.products.ProductsApiProxy;
import com.market.purchase.integration.products.services.ProductsService;
import com.market.purchase.model.ProductLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProductsServiceImpl implements ProductsService {
    @Autowired
    private ProductsApiProxy productsApiProxy;

    @Override
    public ResponseEntity<ProductLock> lockProductQuantity(ProductLock productLock) {
        return this.productsApiProxy.lockProductQuantity(productLock);
    }

    @Override
    public ResponseEntity<Void> deleteLock(String idLock) {
        return this.productsApiProxy.deleteLock(idLock);
    }
}
