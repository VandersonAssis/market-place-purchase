package com.market.purchase.services.impl;

import com.market.purchase.documents.HistoryDocument;
import com.market.purchase.integration.products.ProductsApiProxy;
import com.market.purchase.repositories.HistoryRepository;
import com.market.purchase.services.HistoryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.NoSuchElementException;
import java.util.Optional;

import static com.market.purchase.TestDataBuilder.buildHistoryDocument;
import static com.market.purchase.TestDataBuilder.buildProductLock;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.OK;

@RunWith(SpringRunner.class)
public class HistoryServiceImplTest {
    private HistoryService historyService;

    @Mock
    private HistoryRepository historyRepository;

    @Mock
    private ProductsApiProxy productsApiProxy;

    @Before
    public void setUp() {
        this.historyService = new HistoryServiceImpl();
        ReflectionTestUtils.setField(this.historyService, "historyRepository", this.historyRepository);
        ReflectionTestUtils.setField(this.historyService, "productsApiProxy", this.productsApiProxy);
    }

    @Test
    public void shouldHandlePurchaseHistoryUpdateAndCallFindLockById() {
        HistoryDocument historyDocument = buildHistoryDocument();
        when(this.historyRepository.findByLockId(anyString())).thenReturn(Optional.of(historyDocument));
        when(this.historyRepository.save(any(HistoryDocument.class))).thenReturn(historyDocument);
        when(this.productsApiProxy.deleteLock(anyString())).thenReturn(new ResponseEntity<>(OK));

        this.historyService.handlePurchaseHistoryUpdate(buildProductLock());

        verify(this.historyRepository, times(1)).findByLockId(anyString());
    }

    @Test
    public void shouldHandlePurchaseHistoryUpdateAndCallRepositorySave() {
        HistoryDocument historyDocument = buildHistoryDocument();
        when(this.historyRepository.findByLockId(anyString())).thenReturn(Optional.of(historyDocument));
        when(this.historyRepository.save(any(HistoryDocument.class))).thenReturn(historyDocument);
        when(this.productsApiProxy.deleteLock(anyString())).thenReturn(new ResponseEntity<>(OK));

        this.historyService.handlePurchaseHistoryUpdate(buildProductLock());

        verify(this.historyRepository, times(1)).save(historyDocument);
    }

    @Test
    public void shouldHandlePurchaseHistoryUpdateAndCallProxyDeleteLock() {
        HistoryDocument historyDocument = buildHistoryDocument();
        when(this.historyRepository.findByLockId(anyString())).thenReturn(Optional.of(historyDocument));
        when(this.historyRepository.save(any(HistoryDocument.class))).thenReturn(historyDocument);
        when(this.productsApiProxy.deleteLock(anyString())).thenReturn(new ResponseEntity<>(OK));

        this.historyService.handlePurchaseHistoryUpdate(buildProductLock());

        verify(this.productsApiProxy, times(1)).deleteLock(anyString());
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldHandlePurchaseHistoryUpdateAndThrowExceptionWhenNoPreviousLockHistoryFound() {
        HistoryDocument historyDocument = buildHistoryDocument();
        when(this.historyRepository.findByLockId(anyString())).thenReturn(Optional.empty());
        when(this.historyRepository.save(any(HistoryDocument.class))).thenReturn(historyDocument);
        when(this.productsApiProxy.deleteLock(anyString())).thenReturn(new ResponseEntity<>(OK));

        this.historyService.handlePurchaseHistoryUpdate(buildProductLock());
    }
}