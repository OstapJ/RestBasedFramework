package com.epam.rest.dto;

/**
 * Created by Ievgen_Ostapenko on 2/13/2017.
 */
public class DashboardDTO
{
    private String owner;
    private String isShared;
    private String name;

    public String getOwner()
    {
        return owner;
    }

    public void setOwner(String owner)
    {
        this.owner = owner;
    }

    public String getIsShared()
    {
        return isShared;
    }

    public void setIsShared(String isShared)
    {
        this.isShared = isShared;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
