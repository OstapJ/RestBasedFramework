package com.epam.rest.rest;

import com.epam.rest.dto.CreateDashboardDTO;
import com.epam.rest.dto.DashboardDTO;
import com.epam.rest.dto.GeneralResponseDTO;
import com.epam.rest.model.HttpResponseModel;
import com.epam.rest.model.annotation.*;

@RestfulClient
public interface DashboardService {

    @Get
    @ResourcePath("/{projectName}/dashboard")
    @HttpResponse
    HttpResponseModel<DashboardDTO[]> getDashboards(@Path("projectName") String projectName);

    @Get
    @ResourcePath("/{projectName}/dashboard/{dashboardId}")
    @HttpResponse
    HttpResponseModel<DashboardDTO> getDashboard(@Path("projectName") String projectName, @Path("dashboardId") String dashboardId);

    @Post
    @ResourcePath("/{projectName}/dashboard")
    @HttpResponse
    HttpResponseModel<GeneralResponseDTO> postDashboard(@Path("projectName") String projectName, @Body CreateDashboardDTO dashboardDTO);

    @Delete
    @ResourcePath("/{projectName}/dashboard/{dashboardId}")
    @HttpResponse
    HttpResponseModel<Void> deleteDashboard(@Path("projectName") String projectName, @Path("dashboardId") String dashboardId);
}
