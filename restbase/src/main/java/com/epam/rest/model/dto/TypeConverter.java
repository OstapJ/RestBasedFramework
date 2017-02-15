package com.epam.rest.model.dto;


public abstract class TypeConverter {

    protected abstract Object handle(String value);

    protected abstract Class<?> target();

    public void validate(Object value){
        if(!value.getClass().equals(String.class)){
            throw new TypeConverterError(new StringBuilder()
                    .append("Not able to convert {")
                    .append(value.getClass()).append("} value {")
                    .append(value).append("} to {").append(target()).append("}").toString());
        }
    }

    public Object convert(Object value){
        if(null == value){
            return null;
        }
        validate(value);
        return handle(String.class.cast(value));
    }
}
