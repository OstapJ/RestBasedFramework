package com.epam.rest.rest;

import java.util.List;

import com.epam.rest.dto.WidgetDTO;
import com.epam.rest.model.HttpResponseModel;
import com.epam.rest.model.annotation.Get;
import com.epam.rest.model.annotation.HttpResponse;
import com.epam.rest.model.annotation.Path;
import com.epam.rest.model.annotation.ResourcePath;
import com.epam.rest.model.annotation.RestfulClient;

@RestfulClient
public interface WidgetService
{

    @Get
    @ResourcePath("/{projectName}/widget/names/all")
    @HttpResponse
    HttpResponseModel<List<String>> getWidgetNames(@Path("projectName") String projectName);

    @Get
    @ResourcePath("/{projectName}/widget/{widgetId}")
    @HttpResponse
    HttpResponseModel<WidgetDTO> getWidget(@Path("projectName") String projectName, @Path("widgetId") String widgetId);

}
