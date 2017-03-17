package com.epam.rest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**
 * Created by Ievgen_Ostapenko on 2/13/2017.
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WidgetDTO {
    private String owner;
    private String isShared;
    private String name;
}
