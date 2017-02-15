package com.epam.rest.model.dto;

public class StringConverter extends TypeConverter {

    @Override
    protected Object handle(String value) {
        return value;
    }

    @Override
    protected Class<?> target() {
        return String.class;
    }
}
