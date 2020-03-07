package com.market.purchase.integration.products.services.impl;

import com.market.purchase.TestDataBuilder;
import com.market.purchase.integration.products.ProductsApiProxy;
import com.market.purchase.integration.products.services.ProductsService;
import com.market.purchase.model.ProductLock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.jdbc.TestDatabaseAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static com.market.purchase.TestDataBuilder.buildProductLock;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.OK;

@RunWith(SpringRunner.class)
public class ProductsServiceImplTest {
    private ProductsService productsService;

    @Mock
    private ProductsApiProxy productsApiProxy;

    @Before
    public void setUp() {
        this.productsService = new ProductsServiceImpl();
        ReflectionTestUtils.setField(this.productsService, "productsApiProxy", this.productsApiProxy);
    }

    @Test
    public void lockProductQuantityShouldCallProductsProxyLockProductQuantity() {
        ProductLock productLock = buildProductLock();
        when(this.productsApiProxy.lockProductQuantity(any(ProductLock.class))).thenReturn(new ResponseEntity<>(productLock, OK));

        this.productsService.lockProductQuantity(productLock);
        verify(this.productsApiProxy, times(1)).lockProductQuantity(any(ProductLock.class));
    }

    @Test
    public void shouldLockProductQuantityAndForwardProxyResponse() {
        ProductLock productLock = buildProductLock();
        when(this.productsApiProxy.lockProductQuantity(any(ProductLock.class))).thenReturn(new ResponseEntity<>(productLock, OK));

        ResponseEntity<ProductLock> response = this.productsService.lockProductQuantity(productLock);
        assertEquals(OK, response.getStatusCode());
    }

    @Test
    public void shouldDeleteLockAndCallProductsApiProxyDeleteLock() {
        when(this.productsApiProxy.deleteLock(anyString())).thenReturn(new ResponseEntity<>(OK));
        this.productsService.deleteLock("test_lock_id");

        verify(this.productsApiProxy, times(1)).deleteLock(anyString());
    }

    @Test
    public void shouldDeleteLockAndForwardProxyResponse() {
        when(this.productsApiProxy.deleteLock(anyString())).thenReturn(new ResponseEntity<>(OK));
        ResponseEntity<Void> response = this.productsService.deleteLock("test_lock_id");

        assertEquals(OK, response.getStatusCode());
    }
}