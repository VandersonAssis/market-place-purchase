package com.market.purchase.amqp.consumers;

import com.google.gson.Gson;
import com.market.purchase.model.ProductLock;
import com.market.purchase.services.HistoryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderProcessedConsumer {
    private static final Logger log = LogManager.getLogger(OrderProcessedConsumer.class);

    @Autowired
    private HistoryService historyService;

    @RabbitListener(queues = "${amqp.mpop.order.processed.queue}")
    public void consume(String payload) throws Exception {
        log.info("Begin");
        ProductLock productLock = new Gson().fromJson(payload, ProductLock.class);
        log.info("ProductId is {} and OrderStatus is {}", productLock.getIdProduct(), productLock.getOrderStatus());

        this.historyService.handlePurchaseHistoryUpdate(productLock);
    }
}
