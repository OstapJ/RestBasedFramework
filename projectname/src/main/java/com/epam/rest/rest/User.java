package com.epam.rest.rest;

import com.epam.rest.dto.UserDTO;

import com.epam.rest.model.HttpResponseModel;
import com.epam.rest.model.annotation.Get;
import com.epam.rest.model.annotation.HttpResponse;
import com.epam.rest.model.annotation.ResourcePath;
import com.epam.rest.model.annotation.RestfulClient;

@RestfulClient
public interface User {

    @Get
    @ResourcePath("/user")
    @HttpResponse
    HttpResponseModel<UserDTO> getUser();

}
