package com.experian;

import com.experian.controllers.AlbumsApiController;
import com.experian.controllers.PhotosApiController;
import com.jayway.jsonpath.JsonPath;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.minidev.json.JSONArray;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class ExperianTests {

    protected ThreadLocal<RequestSpecification> requestSpec = new ThreadLocal<RequestSpecification>();

    /**
     * Verify the response code
     *
     * @param response
     * @param expectedCode
     */
    public void validateResponse(Response response, int expectedCode) {
        Assert.assertTrue(response.getStatusCode() == expectedCode,
                "Incorrect status code is received. Expected: " + expectedCode + ", "
                        + response.getStatusCode());
    }

    /**
     * Make GET photos request and validate the response status
     *
     * @param specification
     * @param statusCode
     * @return
     */
    public Response getPhotosApiResponse(RequestSpecification specification, int statusCode) {
        PhotosApiController photosApi = new PhotosApiController();
        Response photosRes = photosApi.getPhotos(specification);
        validateResponse(photosRes, statusCode);
        return photosRes;
    }

    /**
     * Make GET albums request and validate the response status
     *
     * @param specification
     * @param statusCode
     * @return
     */
    public Response getAlbumsApiResponse(RequestSpecification specification, int statusCode) {
        AlbumsApiController albumsApi = new AlbumsApiController();
        Response albumsRes = albumsApi.getAlbums(specification);
        validateResponse(albumsRes, statusCode);
        return albumsRes;
    }

    /**
     * Filter the JSON response and verify the expected albums count
     *
     * @param response
     * @param userId
     * @param expected
     */
    public void validateAlbumsResponse(Response response, int userId, int expected) {
        List<Integer> userIds = JsonPath.parse(response.asString()).read("$.[?(@.userId == " + userId + ")]");
        Assert.assertEquals(expected, userIds.size(),
                "User " + userId + " doesn't have " + expected + " albums. Got " + userIds.size() + " albums only");
    }

    public void validateUserOwnsAlbum(Response response, int userId, int albumId) {
        JSONArray userRes = JsonPath.parse(response.asString()).read("$.[?(@.userId == " + userId + ")]");
        JSONArray albumIds = JsonPath.parse(response.asString()).read("$.[?(@.id == " + albumId + ")]");
        Assert.assertEquals(1, albumIds.size(), "User " + userId + " doesn't own " + albumId + " album.");
    }

    /**
     * Validate photos response for the given album id
     *
     * @param response
     * @param albumId
     * @param expected
     */
    public void validatePhotosResponse(Response response, int albumId, int expected) {
        JSONArray albumIds = JsonPath.parse(response.asString()).read("$.[?(@.albumId == " + albumId + ")]");
        Assert.assertEquals(expected, albumIds.size(), "Album ");
    }

    @BeforeClass
    public void buildRequest() {
        RequestSpecification specification = new RequestSpecBuilder().build();
        specification.baseUri("https://jsonplaceholder.typicode.com");
        specification.contentType(ContentType.JSON);
        specification.accept(ContentType.JSON);
        requestSpec.set(specification);
    }

    @Test(description = "A test is successful if 'User 4 has 10 albums'")
    public void testUser4AlbumsCount() {
        RequestSpecification specification = requestSpec.get();
        Response albumsRes = getAlbumsApiResponse(specification, 200);
        validateAlbumsResponse(albumsRes, 4, 10);
    }

    @Test(description = " A test is successful if 'User 2 has 10 albums'")
    public void testUser2AlbumsCount() {
        RequestSpecification specification = requestSpec.get();
        Response albumsRes = getAlbumsApiResponse(specification, 200);
        validateAlbumsResponse(albumsRes, 2, 10);
    }

    @Test(description = "A test is successful if 'User 4 owns album 34 and that album contains 50 photos'")
    public void testUser4AlbumsAndPhotos() {
        RequestSpecification specification = requestSpec.get();
        Response photosRes = getPhotosApiResponse(specification, 200);
        Response albumsRes = getAlbumsApiResponse(specification, 200);

        int userId = 4;
        int albumId = 34;
        int expectedPhotosCount = 50;

        validateUserOwnsAlbum(albumsRes, userId, albumId);
        validatePhotosResponse(photosRes, albumId, expectedPhotosCount);
    }

}
