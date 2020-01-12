package com.market.purchase.integration.products;

import com.market.purchase.model.ProductLock;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(name = "${products.spring.application.name}")
@RibbonClient(name = "${products.spring.application.name}")
public interface ProductsApiProxy {
    @PostMapping("/marketplace/api/v1/products/lock")
    ResponseEntity<ProductLock> lockProductQuantity(@Valid @RequestBody ProductLock productLock);

    @DeleteMapping("/marketplace/api/v1/products/{idLock}/lock")
    ResponseEntity<Void> deleteLock(@PathVariable("idLock") String idLock);
}
