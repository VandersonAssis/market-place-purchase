package com.market.purchase.amqp.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@EnableRabbit
@Configuration
public class AmqpConfig {

    @Value("${amqp.mppu.process.order.queue}")
    private String processOrderQueueName;

    @Value("${amqp.mppu.process.order.queue.ex}")
    private String processOrderQueueExchangeName;

    @Value("${amqp.mppu.process.order.dlq}")
    private String processOrderDlqName;

    @Value("${amqp.mppu.process.order.dlx}")
    private String processOrderDlxName;

    @Value("${amqp.mppu.process.order.routing.key}")
    private String processOrderRoutingKey;

    @Bean
    public Queue processOrderQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", this.processOrderDlxName);
        args.put("x-dead-letter-routing-key", this.processOrderRoutingKey);
        return new Queue(this.processOrderQueueName, true, false, false, args);
    }

    @Bean
    public Declarables processOrderBindings() {
        Queue processOrderQueue = this.processOrderQueue();
        TopicExchange processOrderExchange = new TopicExchange(this.processOrderQueueExchangeName);

        return new Declarables(processOrderQueue, processOrderExchange, BindingBuilder.bind(processOrderQueue)
                .to(processOrderExchange).with(this.processOrderRoutingKey));
    }

    @Bean
    public Queue processOrderDlq() {
        return new Queue(this.processOrderDlqName);
    }

    @Bean
    public TopicExchange processOrderDlx() {
        return new TopicExchange(this.processOrderDlxName);
    }

    @Bean
    public Binding bindProcessOrderDlq() {
        return BindingBuilder.bind(this.processOrderDlq()).to(this.processOrderDlx())
                .with(this.processOrderRoutingKey);
    }

    public String processOrderQueueExchangeName() {
        return this.processOrderQueueExchangeName;
    }
}
