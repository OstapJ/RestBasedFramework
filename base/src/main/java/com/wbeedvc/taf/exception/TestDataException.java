package com.wbeedvc.taf.exception;

/**
 * Created by Ostap on 22.03.2017.
 */
public class TestDataException extends RuntimeException {
    public TestDataException(final String message) {
        super(message);
    }

    public TestDataException(final String messageTemplate, Object... args) {
        super(String.format(messageTemplate, args));
    }
}
