package com.market.purchase.amqp.impl;

import com.market.purchase.amqp.AmqpService;
import com.market.purchase.amqp.config.AmqpConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static com.market.purchase.TestDataBuilder.buildProductLock;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class AmqpServiceImplTest {
    private AmqpService amqpService;

    @Mock
    private AmqpConfig amqpConfig;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Before
    public void setUp() {
        this.amqpService = new AmqpServiceImpl();
        ReflectionTestUtils.setField(this.amqpService, "amqpConfig", this.amqpConfig);
        ReflectionTestUtils.setField(this.amqpService, "rabbitTemplate", this.rabbitTemplate);
    }

    @Test
    public void shouldSentToQueueAndCallRabbitTemplateConvertAndSend() {
        doNothing().when(this.rabbitTemplate).convertAndSend(anyString(), anyString(), anyString());
        when(this.amqpConfig.processOrderQueueExchangeName()).thenReturn("test_exchange_name");
        this.amqpService.sendToProcessOrderQueue(buildProductLock());

        verify(this.rabbitTemplate, times(1)).convertAndSend(anyString(), anyString(), anyString());
    }
}