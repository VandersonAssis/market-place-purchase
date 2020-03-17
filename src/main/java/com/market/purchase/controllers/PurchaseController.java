package com.market.purchase.controllers;

import com.market.purchase.amqp.AmqpService;
import com.market.purchase.api.PurchaseApi;
import com.market.purchase.documents.HistoryDocument;
import com.market.purchase.exceptions.custom.BaseHttpException;
import com.market.purchase.exceptions.exceptionhandlers.ApiError;
import com.market.purchase.integration.products.services.ProductsService;
import com.market.purchase.model.ProductLock;
import com.market.purchase.repositories.HistoryRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

import static org.springframework.http.HttpStatus.*;

@RestController
public class PurchaseController extends BaseController implements PurchaseApi {
    private static final Logger log = LogManager.getLogger(PurchaseController.class);

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ProductsService productsService;

    @Autowired
    private AmqpService amqpService;

    @Autowired
    private HistoryRepository historyRepository;

    @Override
    public ResponseEntity startPurchase(ProductLock productLock) {
        log.info("Begin with {} idProduct, {} quantity and {} orderStatus", productLock.getIdProduct(), productLock.getQuantity(),
                productLock.getOrderStatus());

        if(productLock.getQuantity() <= 0)
            throw new BaseHttpException(new ApiError(BAD_REQUEST, this.messageSource.getMessage("zeroed.purchase.quantity",
                    null, Locale.getDefault())));

        ResponseEntity<ProductLock> lockResponse = this.productsService.lockProductQuantity(productLock);
        if(lockResponse.getStatusCode() != OK) {
            log.error("Unable to lock product {} with quantity {}. Response is {}", productLock.getIdProduct(), productLock.getQuantity(),
                    lockResponse.getStatusCode());

            return lockResponse;
        }

        this.historyRepository.save(HistoryDocument.build(lockResponse.getBody()));
        this.amqpService.sendToProcessOrderQueue(lockResponse.getBody());

        log.info("Purchase started successfully for {} product, {} quantity and {} orderStatus", productLock.getIdProduct(),
                productLock.getQuantity(), productLock.getOrderStatus());

        return new ResponseEntity(ACCEPTED);
    }
}
