package com.market.purchase.amqp;

public interface AmqpService {
    void sendToProcessOrderQueue(String payload);
}
