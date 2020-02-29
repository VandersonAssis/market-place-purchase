package com.market.purchase.controllers;

import com.market.purchase.amqp.AmqpService;
import com.market.purchase.api.PurchaseApi;
import com.market.purchase.documents.HistoryDocument;
import com.market.purchase.exceptions.custom.BaseHttpException;
import com.market.purchase.exceptions.exceptionhandlers.ApiError;
import com.market.purchase.integration.products.ProductsApiProxy;
import com.market.purchase.model.ProductLock;
import com.market.purchase.repositories.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.*;

@RestController
public class PurchaseController extends BaseController implements PurchaseApi {
    @Autowired
    private ProductsApiProxy productsApiProxy;

    @Autowired
    private AmqpService amqpService;

    @Autowired
    private HistoryRepository historyRepository;

    @Override
    public ResponseEntity startPurchase(ProductLock productLock) {
        if(productLock.getQuantity() <= 0)
            throw new BaseHttpException(new ApiError(BAD_REQUEST, "Purchase quantity is zero"));

        ResponseEntity<ProductLock> lockResponse = this.productsApiProxy.lockProductQuantity(productLock);
        if(lockResponse.getStatusCode() != OK)
            return lockResponse;

        this.historyRepository.save(HistoryDocument.build(lockResponse.getBody()));
        this.amqpService.sendToProcessOrderQueue(lockResponse.getBody());

        return new ResponseEntity(ACCEPTED);
    }
}
