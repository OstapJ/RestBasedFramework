package com.epam.rest.model.dto;

import java.util.Date;

public class DateConverter extends TypeConverter {

    @Override
    public Date handle(String value) {
        return new Date(value);
    }

    @Override
    protected Class<?> target() {
        return Date.class;
    }
}
