package com.market.purchase.amqp.consumers;

import com.google.gson.Gson;
import com.market.purchase.TestDataBuilder;
import com.market.purchase.model.ProductLock;
import com.market.purchase.services.HistoryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.jdbc.TestDatabaseAutoConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static com.market.purchase.TestDataBuilder.buildProductLock;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class OrderProcessedConsumerTest {
    private OrderProcessedConsumer orderProcessedConsumer;

    @Mock
    private HistoryService historyService;

    @Before
    public void setUp() {
        this.orderProcessedConsumer = new OrderProcessedConsumer();
        ReflectionTestUtils.setField(this.orderProcessedConsumer, "historyService", this.historyService);
    }

    @Test
    public void shouldConsumeQueueAndUpdateHistory() throws Exception {
        doNothing().when(this.historyService).handlePurchaseHistoryUpdate(any(ProductLock.class));
        this.orderProcessedConsumer.consume(new Gson().toJson(buildProductLock()));

        verify(this.historyService, times(1)).handlePurchaseHistoryUpdate(any(ProductLock.class));
    }
}