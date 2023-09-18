package com.vmavropo.api;

import com.vmavropo.utils.Test;
import com.vmavropo.utils.factory.ContextFactory;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.Getter;

import java.util.Map;

public class CommonAPI {


    @Getter
    CommonAPIValidations validate;


    Test test;

    ContextFactory context;

    public CommonAPI(Test test) {
        this.test = test;
        validate = new CommonAPIValidations(test);
        context = test.context();
    }

    public Response apiGenericPostJSON(String endPoint, String body, Map<String, Object> headerMap, String token) {
        return RestAssured.given().log().all()
                .auth()
                .oauth2(token)
                .relaxedHTTPSValidation().log().all()
                .baseUri(endPoint)
                .contentType(ContentType.JSON)
                .body(body)
                .headers(headerMap)
                .post()
                .then().log().all()
                .extract()
                .response();
    }

    public Response apiGenericPostJSON(String endPoint, String body, Map<String, String> headerMap) {
        return RestAssured.given().log().all()
                .relaxedHTTPSValidation()
                .baseUri(endPoint)
                .contentType(ContentType.JSON)
                .body(body)
                .headers(headerMap)
                .post()
                .then().log().all()
                .extract()
                .response();
    }

    public Response apiGenericPostXML(String endPoint, Map<String, String> params) {
        return RestAssured.given().relaxedHTTPSValidation().accept(ContentType.XML).
                log().all().params(params).post(endPoint)
                .then().log().all()
                .extract().response();
    }

    public Response apiGenericPostXML(String loginToken, String body, String url) {
        return RestAssured.given().relaxedHTTPSValidation().accept(ContentType.XML).
                log().all().header("Authorization", "Bearer " + loginToken).when().log().all()
                .body(body).post(url).then().log().all().extract().response();
    }

    public Response apiGenericPostXML(String endPoint, String body, Map<String, Object> headerMap) {
        return RestAssured.given().relaxedHTTPSValidation().log().all()
                .baseUri(endPoint)
                .contentType(ContentType.XML)
                .body(body)
                .headers(headerMap)
                .post()
                .then().log().all()
                .extract()
                .response();
    }
}
