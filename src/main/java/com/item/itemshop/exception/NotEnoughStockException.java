package com.item.itemshop.exception;

public class NotEnoughStockException extends RuntimeException {
    protected NotEnoughStockException() {}

    public NotEnoughStockException(String message) {
        super(message);
    }
    public NotEnoughStockException(String message, Throwable cause) {
        super(message, cause);
    }
    public NotEnoughStockException(Throwable cause) {
        super(cause);
    }
}

