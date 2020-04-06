package com.market.purchase.amqp;

import com.market.purchase.model.ProductLock;

public interface AmqpService {
    void sendToProcessOrderQueue(ProductLock payload);
}
