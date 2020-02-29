package com.market.purchase.exceptions.custom;

import com.market.purchase.exceptions.exceptionhandlers.ApiError;

public class BaseHttpException extends RuntimeException {
    private ApiError apiError;

    public BaseHttpException(ApiError apiError) {
        this.apiError = apiError;
    }

    public ApiError getApiError() {
        return this.apiError;
    }
}
