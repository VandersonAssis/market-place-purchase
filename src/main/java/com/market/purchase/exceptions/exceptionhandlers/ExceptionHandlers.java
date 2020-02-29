package com.market.purchase.exceptions.exceptionhandlers;

import com.market.purchase.exceptions.custom.BaseHttpException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class ExceptionHandlers extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {

        return new ResponseEntity<>(ex.getBindingResult().getFieldErrors().stream()
                .map(error -> new ApiError(BAD_REQUEST, error.toString())).collect(Collectors.toSet()), BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {

        return new ResponseEntity<>(new ApiError(BAD_REQUEST, "Malformed request", ex.getMessage()), BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
                                                                   HttpStatus status, WebRequest request) {

        String message = "The resource requested was not found.";
        return new ResponseEntity<>(new ApiError(NOT_FOUND, message), NOT_FOUND);
    }

    @ExceptionHandler(value = BaseHttpException.class)
    protected ResponseEntity<Object> handleCustomBaseHttpException(BaseHttpException ex) {
        return new ResponseEntity<>(ex.getApiError(), ex.getApiError().getStatus());
    }

    @ExceptionHandler(value = RuntimeException.class)
    protected ResponseEntity<Object> handleInternalServerErrorException(RuntimeException ex, WebRequest request) {
        String message = "Internal server error. A more meaningful log has been forwarded to our team.";
        return new ResponseEntity<>(new ApiError(INTERNAL_SERVER_ERROR, message), INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = DuplicateKeyException.class)
    protected ResponseEntity<Object> handleConflictException(DuplicateKeyException ex, WebRequest request) {
        String message = "This record is already in the database.";
        return new ResponseEntity<>(new ApiError(CONFLICT, message), CONFLICT);
    }
}
