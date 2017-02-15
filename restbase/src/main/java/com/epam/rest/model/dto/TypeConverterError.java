package com.epam.rest.model.dto;


public class TypeConverterError extends RuntimeException {

    public TypeConverterError() {
    }

    public TypeConverterError(String message) {
        super(message);
    }

    public TypeConverterError(String message, Throwable cause) {
        super(message, cause);
    }

    public TypeConverterError(Throwable cause) {
        super(cause);
    }

    public TypeConverterError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
