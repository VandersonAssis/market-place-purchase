package com.market.purchase.exceptions.exceptionhandlers;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

public class ClientExceptionHandlers extends DefaultResponseErrorHandler {
    @Override
    public void handleError(ClientHttpResponse response) {
        //Had to implement this so spring stop throwing exceptions on restTemplate calls
    }
}
