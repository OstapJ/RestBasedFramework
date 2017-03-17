package com.epam.rest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Created by Ievgen_Ostapenko on 2/13/2017.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    private String userId;
    private String email;
    @JsonProperty("full_name")
    private String fullName;
    @JsonProperty("account_type")
    private String accountType;
    private String userRole;
    @JsonProperty("default_project")
    private String defaultProject;
}
