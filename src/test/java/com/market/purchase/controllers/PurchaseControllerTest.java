package com.market.purchase.controllers;

import com.google.gson.Gson;
import com.market.purchase.amqp.AmqpService;
import com.market.purchase.documents.HistoryDocument;
import com.market.purchase.exceptions.exceptionhandlers.ExceptionHandlers;
import com.market.purchase.integration.products.ProductsApiProxy;
import com.market.purchase.integration.products.services.ProductsService;
import com.market.purchase.model.ProductLock;
import com.market.purchase.repositories.HistoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.market.purchase.TestDataBuilder.buildHistoryDocument;
import static com.market.purchase.TestDataBuilder.buildProductLock;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class PurchaseControllerTest {
    @InjectMocks
    private PurchaseController purchaseController;

    @Mock
    private ProductsService productsService;

    @Mock
    private AmqpService amqpService;

    @Mock
    private HistoryRepository historyRepository;

    @Mock
    private MessageSource msg;

    private MockMvc mockMvc;
    private String apiPrefix;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.purchaseController)
                .setControllerAdvice(new ExceptionHandlers())
                .build();

        this.apiPrefix = "/marketplace/api/v1";
    }

    @Test
    public void shouldStartPurchaseAndReturnAccepted() throws Exception {
        ProductLock productLock = buildProductLock();
        when(this.productsService.lockProductQuantity(any(ProductLock.class))).thenReturn(new ResponseEntity<>(productLock, OK));
        when(this.historyRepository.save(any(HistoryDocument.class))).thenReturn(buildHistoryDocument());
        doNothing().when(this.amqpService).sendToProcessOrderQueue(any(ProductLock.class));

        this.mockMvc.perform(post(this.apiPrefix + "/purchase/start")
            .contentType(APPLICATION_JSON).content(new Gson().toJson(productLock)))
                .andExpect(status().isAccepted());
    }

    @Test
    public void shouldTryToStartZeroedQuantityPurchaseAndReturnBadRequest() throws Exception {
        ProductLock productLock = buildProductLock();
        productLock.setQuantity(0);

        when(this.productsService.lockProductQuantity(any(ProductLock.class))).thenReturn(new ResponseEntity<>(productLock, OK));
        when(this.historyRepository.save(any(HistoryDocument.class))).thenReturn(buildHistoryDocument());
        doNothing().when(this.amqpService).sendToProcessOrderQueue(any(ProductLock.class));

        this.mockMvc.perform(post(this.apiPrefix + "/purchase/start")
                .contentType(APPLICATION_JSON).content(new Gson().toJson(productLock)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldTryToStartNegativeQuantityPurchaseAndReturnBadRequest() throws Exception {
        ProductLock productLock = buildProductLock();
        productLock.setQuantity(-1);

        when(this.productsService.lockProductQuantity(any(ProductLock.class))).thenReturn(new ResponseEntity<>(productLock, OK));
        when(this.historyRepository.save(any(HistoryDocument.class))).thenReturn(buildHistoryDocument());
        doNothing().when(this.amqpService).sendToProcessOrderQueue(any(ProductLock.class));

        this.mockMvc.perform(post(this.apiPrefix + "/purchase/start")
                .contentType(APPLICATION_JSON).content(new Gson().toJson(productLock)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldForwardLockResponseWhenReturnIsNotOk() throws Exception {
        ProductLock productLock = buildProductLock();

        when(this.productsService.lockProductQuantity(any(ProductLock.class)))
                .thenReturn(new ResponseEntity<>(productLock, INTERNAL_SERVER_ERROR));
        when(this.historyRepository.save(any(HistoryDocument.class))).thenReturn(buildHistoryDocument());
        doNothing().when(this.amqpService).sendToProcessOrderQueue(any(ProductLock.class));

        this.mockMvc.perform(post(this.apiPrefix + "/purchase/start")
                .contentType(APPLICATION_JSON).content(new Gson().toJson(productLock)))
                .andExpect(status().isInternalServerError());
    }
}