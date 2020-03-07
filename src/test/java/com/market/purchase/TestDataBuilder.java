package com.market.purchase;

import com.market.purchase.documents.HistoryDocument;
import com.market.purchase.model.ProductLock;

public abstract class TestDataBuilder {
    public static ProductLock buildProductLock() {
        return new ProductLock()
            .lockId("lock_test_id")
            .idProduct("test_id_product")
            .quantity(10);
    }

    public static HistoryDocument buildHistoryDocument() {
        return HistoryDocument.build(buildProductLock());
    }
}
