package com.market.purchase.config;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class AmqpConfig {

    @Value("${amqp.process.order.queue.name}")
    private String processOrderQueue;

    @Value("${amqp.process.order.queue.name.ex}")
    private String processOrderQueueExchange;

    @Bean
    public Declarables topicExchangeBindings() {
        Queue orderQueue = new Queue(this.processOrderQueue);
        TopicExchange orderExchange = new TopicExchange(this.processOrderQueueExchange);

        return new Declarables(orderQueue, orderExchange, BindingBuilder.bind(orderQueue).to(orderExchange).with("PROCESS_ORDER"));
    }

    public String processOrderQueueExchangeName() {
        return this.processOrderQueueExchange;
    }
}
