package com.pragma.traceability.domain.exception;

public class OrderNotFromCustomerException extends RuntimeException {

    public OrderNotFromCustomerException(String message) {
        super(message);
    }
}
