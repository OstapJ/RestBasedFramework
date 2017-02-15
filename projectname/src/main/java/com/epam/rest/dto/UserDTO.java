package com.epam.rest.dto;

/**
 * Created by Ievgen_Ostapenko on 2/13/2017.
 */
public class UserDTO {
    private String userId;
    private String email;
    private String full_name;
    private String account_type;
    private String userRole;
    private String default_project;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getDefault_project() {
        return default_project;
    }

    public void setDefault_project(String default_project) {
        this.default_project = default_project;
    }
}
