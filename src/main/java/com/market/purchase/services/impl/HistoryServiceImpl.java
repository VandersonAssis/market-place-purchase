package com.market.purchase.services.impl;

import com.market.purchase.documents.HistoryDocument;
import com.market.purchase.integration.products.services.ProductsService;
import com.market.purchase.model.ProductLock;
import com.market.purchase.repositories.HistoryRepository;
import com.market.purchase.services.HistoryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

@Service
public class HistoryServiceImpl implements HistoryService {
    private static final Logger log = LogManager.getLogger(HistoryServiceImpl.class);

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private ProductsService productsService;

    @Override
    public void handlePurchaseHistoryUpdate(ProductLock productLock) {
        log.info("Begin for {} product with idLock of {} and orderStatus is {}", productLock.getIdProduct(),
                productLock.getLockId(), productLock.getOrderStatus());

        HistoryDocument historyDocument = null;
        Optional<HistoryDocument> historyDocumentOptional = this.historyRepository.findByLockId(productLock.getLockId());
        historyDocument = historyDocumentOptional.orElseThrow();

        requireNonNull(historyDocument).setOrderStatus(HistoryDocument.OrderStatus.valueOf(productLock.getOrderStatus().toString()));
        this.historyRepository.save(historyDocument);
        log.info("History saved for {} product and {} idLock", productLock.getIdProduct(), productLock.getLockId());

        this.productsService.deleteLock(productLock.getLockId());
        log.info("Lock {} just saved has been removed from the database", productLock.getLockId());
    }
}
