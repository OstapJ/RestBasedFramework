package com.epam.rest.rest;

import com.epam.rest.dto.LoginDTO;

import com.epam.rest.model.HttpResponseModel;
import com.epam.rest.model.annotation.Body;
import com.epam.rest.model.annotation.Delete;
import com.epam.rest.model.annotation.Get;
import com.epam.rest.model.annotation.HttpResponse;
import com.epam.rest.model.annotation.Path;
import com.epam.rest.model.annotation.Post;
import com.epam.rest.model.annotation.Put;
import com.epam.rest.model.annotation.ResourcePath;
import com.epam.rest.model.annotation.RestfulClient;

@RestfulClient
public interface Login {

    @Get
    @ResourcePath("/cases/{id}")
    @HttpResponse
    HttpResponseModel<LoginDTO> getCase(@Path("id") String id);

    @Post
    @ResourcePath("/cases")
    @HttpResponse
    HttpResponseModel<Void> createCase(@Body LoginDTO caseDTO);

    @Put
    @ResourcePath("/cases/{id}")
    @HttpResponse
    HttpResponseModel<Void> updateCase(@Path("id") String caseID, @Body LoginDTO caseDTO);

    @Delete
    @ResourcePath("/cases/{id}")
    @HttpResponse
    HttpResponseModel<Void> deleteCase(@Path("id") String caseID);

}
