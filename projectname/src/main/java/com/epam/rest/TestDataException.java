package com.epam.rest;

public class TestDataException extends RuntimeException {

    public TestDataException(final String message) {
        super(message);
    }

    public TestDataException(final String messageTemplate, Object... args) {
        super(String.format(messageTemplate, args));
    }
}
