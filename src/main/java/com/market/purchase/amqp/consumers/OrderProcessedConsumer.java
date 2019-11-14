package com.market.purchase.amqp.consumers;

import com.google.gson.Gson;
import com.market.purchase.documents.HistoryDocument;
import com.market.purchase.integration.ProductApiFacade;
import com.market.purchase.model.ProductLock;
import com.market.purchase.repositories.HistoryRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OrderProcessedConsumer {
    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private ProductApiFacade productApiFacade;

    @RabbitListener(queues = "${amqp.order.processed.queue.name}")
    public void consume(String payload) {
        ProductLock productLock = new Gson().fromJson(payload, ProductLock.class);
        this.handlePurchaseHistoryUpdate(productLock);
    }

    private void handlePurchaseHistoryUpdate(ProductLock productLock) {
        HistoryDocument historyDocument = null;
        Optional<HistoryDocument> historyDocumentOptional = this.historyRepository.findByLockId(productLock.getLockId());
        if(historyDocumentOptional.isPresent())
            historyDocument = historyDocumentOptional.orElseThrow();
        
        historyDocument.setOrderStatus(HistoryDocument.OrderStatus.valueOf(productLock.getOrderStatus().toString()));
        this.historyRepository.save(historyDocument);
        this.productApiFacade.deleteLock(productLock.getLockId());
    }
}
