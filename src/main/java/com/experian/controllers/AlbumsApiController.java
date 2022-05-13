package com.experian.controllers;

import com.experian.api.ApiServices;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class AlbumsApiController extends ApiServices {
    private final String albumsResource = "/albums";

    public Response getAlbums(RequestSpecification specification) {
        specification.basePath(albumsResource);
        return getRequest(specification);
    }
}
