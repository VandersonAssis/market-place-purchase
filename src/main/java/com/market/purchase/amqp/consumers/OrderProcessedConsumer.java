package com.market.purchase.amqp.consumers;

import com.google.gson.Gson;
import com.market.purchase.model.ProductLock;
import com.market.purchase.services.HistoryService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderProcessedConsumer {
    @Autowired
    private HistoryService historyService;

    @RabbitListener(queues = "${amqp.mpop.order.processed.queue}")
    public void consume(String payload) throws Exception {
        ProductLock productLock = new Gson().fromJson(payload, ProductLock.class);
        this.historyService.handlePurchaseHistoryUpdate(productLock);
    }
}
