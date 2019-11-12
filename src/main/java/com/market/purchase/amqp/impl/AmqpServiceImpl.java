package com.market.purchase.amqp.impl;

import com.market.purchase.amqp.AmqpService;
import com.market.purchase.config.AmqpConfig;
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
    public void sendToProcessOrderQueue(String payload) {
        this.rabbitTemplate.convertAndSend(this.amqpConfig.processOrderQueueExchangeName(), "PROCESS_ORDER", payload);
    }
}
