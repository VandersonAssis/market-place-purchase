package com.market.purchase.controllers;

import com.market.purchase.amqp.AmqpService;
import com.market.purchase.api.PurchaseApi;
import com.market.purchase.integration.ProductApiFacade;
import com.market.purchase.model.PurchaseProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.OK;

@RestController
public class PurchaseController extends BaseController implements PurchaseApi {
    @Autowired
    private ProductApiFacade productApiFacade;

    @Autowired
    private AmqpService amqpService;

    @Override
    public ResponseEntity startPurchase(PurchaseProduct purchaseProduct) {
        ResponseEntity<String> lockResponse = this.productApiFacade.lockProduct(purchaseProduct);
        if(lockResponse.getStatusCode() != OK)
            return lockResponse;

        this.amqpService.sendToProcessOrderQueue(lockResponse.getBody());

        return new ResponseEntity(ACCEPTED);
    }
}
