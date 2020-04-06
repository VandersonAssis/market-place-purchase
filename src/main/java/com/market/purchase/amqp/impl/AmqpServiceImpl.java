package com.market.purchase.amqp.impl;

import com.google.gson.Gson;
import com.market.purchase.amqp.AmqpService;
import com.market.purchase.amqp.config.AmqpConfig;
import com.market.purchase.model.ProductLock;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AmqpServiceImpl implements AmqpService {
    private static final Logger log = LogManager.getLogger(AmqpServiceImpl.class);

    @Autowired
    private AmqpConfig amqpConfig;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void sendToProcessOrderQueue(ProductLock productLock)  {
        log.info("Product {} and orderStatus {}", productLock.getIdProduct(), productLock.getOrderStatus());
        this.rabbitTemplate.convertAndSend(this.amqpConfig.processOrderQueueExchangeName(),
                "PROCESS_ORDER", new Gson().toJson(productLock));
        log.info("Product {} and orderStatus {} was sent", productLock.getIdProduct(), productLock.getOrderStatus());
    }
}
