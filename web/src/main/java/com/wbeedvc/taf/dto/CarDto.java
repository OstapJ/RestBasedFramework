package com.wbeedvc.taf.dto;

/**
 * Created by Ostap on 26.03.2017.
 */
public class CarDto {
    public static final String NAME = "car";

    private String markDropDown;
    private String modelDropDown;
    private String[] carPrices;

    public String[] getCarPrices() {
        return carPrices;
    }

    public void setCarPrices(String[] carPrices) {
        this.carPrices = carPrices;
    }



    public String getMarkDropDown() {
        return markDropDown;
    }

    public void setMarkDropDown(String markDropDown) {
        this.markDropDown = markDropDown;
    }

    public String getModelDropDown() {
        return modelDropDown;
    }

    public void setModelDropDown(String modelDropDown) {
        this.modelDropDown = modelDropDown;
    }

}
