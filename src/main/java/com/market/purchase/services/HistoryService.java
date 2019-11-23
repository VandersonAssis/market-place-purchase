package com.market.purchase.services;

import com.market.purchase.model.ProductLock;

public interface HistoryService {
    void handlePurchaseHistoryUpdate(ProductLock productLock);
}
