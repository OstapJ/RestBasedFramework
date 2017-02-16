package com.epam.rest.rest;

import com.epam.rest.dto.UserDTO;
import com.epam.rest.dto.WidgetDTO;
import com.epam.rest.model.HttpResponseModel;
import com.epam.rest.model.annotation.Body;
import com.epam.rest.model.annotation.Get;
import com.epam.rest.model.annotation.HttpResponse;
import com.epam.rest.model.annotation.Path;
import com.epam.rest.model.annotation.ResourcePath;
import com.epam.rest.model.annotation.RestfulClient;

import java.util.List;

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
