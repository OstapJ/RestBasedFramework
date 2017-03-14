package com.epam.rest.dto;

/**
 * Created by Ievgen_Ostapenko on 2/13/2017.
 */
public class CreateDashboardDTO
{
    private String share;
    private String name;

    public String getShare() {
        return share;
    }

    public void setShare(String share) {
        this.share = share;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
