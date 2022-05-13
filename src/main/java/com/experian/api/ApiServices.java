package com.experian.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ApiServices {

	public Response getRequest(RequestSpecification specification) {
		return RestAssured.given().spec(specification).when().get().thenReturn();
	}

	public void getRequest(RequestSpecification specification, Class aClass) {
		RestAssured.given().spec(specification).when().get().then().extract().as(aClass);
	}

	public Response postRequest(RequestSpecification specification) {
		return RestAssured.given().spec(specification).when().post().thenReturn();
	}

	public Response deleteRequest(RequestSpecification specification) {
		return RestAssured.given().spec(specification).when().delete().thenReturn();
	}

	public Response putRequest(RequestSpecification specification) {
		return RestAssured.given().spec(specification).when().put().thenReturn();
	}

	public Response patchRequest(RequestSpecification specification) {
		return RestAssured.given().spec(specification).when().patch().thenReturn();
	}

	public boolean validateStatusCode(Response response, int expected) {
		if (response.getStatusCode() != expected) {
			return false;
		}
		return true;
	}

}
