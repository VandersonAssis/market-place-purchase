package com.market.purchase.integration.products.services.impl;

import com.market.purchase.integration.products.ProductsApiProxy;
import com.market.purchase.integration.products.services.ProductsService;
import com.market.purchase.model.ProductLock;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProductsServiceImpl implements ProductsService {
    private static final Logger log = LogManager.getLogger(ProductsServiceImpl.class);

    @Autowired
    private ProductsApiProxy productsApiProxy;

    @Override
    public ResponseEntity<ProductLock> lockProductQuantity(ProductLock productLock) {
        log.info("Locking a quantity of {} of product {}", productLock.getQuantity(), productLock.getIdProduct());
        return this.productsApiProxy.lockProductQuantity(productLock);
    }

    @Override
    public ResponseEntity<Void> deleteLock(String idLock) {
        log.info("Deleting lock with id {}", idLock);
        return this.productsApiProxy.deleteLock(idLock);
    }
}
