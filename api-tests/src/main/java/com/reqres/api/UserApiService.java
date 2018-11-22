package com.reqres.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;

@Slf4j // this is for additional logging log.info("info text here");
public class UserApiService {

    public RequestSpecification setup() {
        return RestAssured
                .given()
                .contentType(ContentType.JSON);
    }

    public ValidatableResponse registerUser(User user) {
        log.info("Registering a new {}", user);
        
        return setup()
                    .log().all()
                    .body(user)
            .when()
                    .post("register")
            .then()
                    .log().all();
    }

    public ValidatableResponse createUser(User user) {
        log.info("Creating a new {}", user);

        return setup()
                .body(user)
                .log().all()
        .when()
                .post("users")
        .then()
                .log().all();
    }

}
