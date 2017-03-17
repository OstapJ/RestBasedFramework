package com.epam.rest.rest;

import com.epam.rest.dto.FacebookStatusDTO;
import com.epam.rest.model.HttpResponseModel;
import com.epam.rest.model.annotation.Get;
import com.epam.rest.model.annotation.HttpResponse;
import com.epam.rest.model.annotation.Post;
import com.epam.rest.model.annotation.ResourcePath;
import com.epam.rest.model.annotation.RestfulClient;

@RestfulClient
public interface FacebookService {

    @Get
    @ResourcePath("https://www.facebook.com/feeds/api_status.php")
    @HttpResponse
    HttpResponseModel<FacebookStatusDTO> getFacebookStatus();

    @Post
    @ResourcePath("https://www.facebook.com/feeds/api_status.php")
    @HttpResponse
    HttpResponseModel<Void> postTestUser();

}
