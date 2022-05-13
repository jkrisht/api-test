package com.experian.controllers;

import com.experian.api.ApiServices;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class PhotosApiController extends ApiServices {
    private final String photosResource = "/photos";

    public Response getPhotos(RequestSpecification specification) {
        specification.basePath(photosResource);
        return getRequest(specification);
    }
}
