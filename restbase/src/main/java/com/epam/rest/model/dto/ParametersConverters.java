package com.epam.rest.model.dto;

import java.util.Date;
import java.util.Map;

import com.google.common.collect.Maps;

public class ParametersConverters {

    private Map<Class<?>, TypeConverter> convertersMap;

    private ParametersConverters(Map<Class<?>, TypeConverter> convertersMap){
        this.convertersMap = convertersMap;
    }

    private static final ParametersConverters CONVERTERS = new ParametersConverters(dtoParamTypeConverters());

    private static Map<Class<?>, TypeConverter> dtoParamTypeConverters(){
        Map<Class<?>, TypeConverter> map = Maps.newHashMap();
        map.put(String.class, new StringConverter());
        map.put(Integer.class, new IntegerConverter());
        map.put(Date.class, new DateConverter());
        return map;
    }

    public static Object convert(Object value, Class<?> type){
        TypeConverter converter =CONVERTERS.getConverter(type);
        return converter != null ? converter.convert(value) : value;
    }

    public TypeConverter getConverter(Class<?> type){
        return convertersMap.get(type);
    }

}
