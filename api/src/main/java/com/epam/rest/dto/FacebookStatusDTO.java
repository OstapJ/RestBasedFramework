package com.epam.rest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Created by Ievgen_Ostapenko on 2/13/2017.
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FacebookStatusDTO {

    @JsonProperty("current")
    private FacebookStatusCurrentDTO facebookStatusCurrentDTO;
    @JsonProperty("push")
    private FacebookStatusPushDTO facebookStatusPushDTO;

}
