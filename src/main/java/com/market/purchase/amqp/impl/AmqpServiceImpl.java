package com.market.purchase.amqp.impl;

import com.google.gson.Gson;
import com.market.purchase.amqp.AmqpService;
import com.market.purchase.amqp.config.AmqpConfig;
import com.market.purchase.model.ProductLock;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AmqpServiceImpl implements AmqpService {
    @Autowired
    private AmqpConfig amqpConfig;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void sendToProcessOrderQueue(ProductLock productLock)  {
        this.rabbitTemplate.convertAndSend(this.amqpConfig.processOrderQueueExchangeName(), "PROCESS_ORDER", new Gson().toJson(productLock));
    }
}
