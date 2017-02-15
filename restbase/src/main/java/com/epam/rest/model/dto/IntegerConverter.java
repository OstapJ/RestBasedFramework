package com.epam.rest.model.dto;

public class IntegerConverter extends TypeConverter {

    @Override
    protected Class<?> target() {
        return Integer.class;
    }

    @Override
    protected Object handle(String value) {
        return Integer.valueOf(value);
    }
}
