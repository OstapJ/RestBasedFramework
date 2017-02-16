package com.epam.rest.rest;

import java.util.List;

import com.epam.rest.dto.DashboardDTO;
import com.epam.rest.dto.WidgetDTO;
import com.epam.rest.model.HttpResponseModel;
import com.epam.rest.model.annotation.Get;
import com.epam.rest.model.annotation.HttpResponse;
import com.epam.rest.model.annotation.Path;
import com.epam.rest.model.annotation.ResourcePath;
import com.epam.rest.model.annotation.RestfulClient;

@RestfulClient
public interface DashboardService
{

    @Get
    @ResourcePath("/{projectName}/dashboard")
    @HttpResponse
    HttpResponseModel<List<DashboardDTO>> getDashboards(@Path("projectName") String projectName);

    @Get
    @ResourcePath("/{projectName}/dashboard/{dashboardId}")
    @HttpResponse
    HttpResponseModel<DashboardDTO> getDashboard(@Path("projectName") String projectName, @Path("dashboardId") String dashboardId);

}
