package com.market.purchase.services.impl;

import com.market.purchase.documents.HistoryDocument;
import com.market.purchase.integration.products.ProductsApiProxy;
import com.market.purchase.model.ProductLock;
import com.market.purchase.repositories.HistoryRepository;
import com.market.purchase.services.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

@Service
public class HistoryServiceImpl implements HistoryService {
    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private ProductsApiProxy productsApiProxy;

    @Override
    public void handlePurchaseHistoryUpdate(ProductLock productLock) {
        HistoryDocument historyDocument = null;
        Optional<HistoryDocument> historyDocumentOptional = this.historyRepository.findByLockId(productLock.getLockId());
        historyDocument = historyDocumentOptional.orElseThrow();

        requireNonNull(historyDocument).setOrderStatus(HistoryDocument.OrderStatus.valueOf(productLock.getOrderStatus().toString()));
        this.historyRepository.save(historyDocument);
        this.productsApiProxy.deleteLock(productLock.getLockId());
    }
}
