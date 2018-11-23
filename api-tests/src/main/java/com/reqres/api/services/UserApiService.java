package com.reqres.api.services;

import com.reqres.api.model.User;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;

@Slf4j // this is for additional logging log.info("info text here");
public class UserApiService {

    public RequestSpecification setup() {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .filters(new RequestLoggingFilter(),
                new ResponseLoggingFilter()); // filters for logging, could be added or removed
    }

    public ValidatableResponse registerUser(User user) {
        log.info("Registering a new {}", user);

        return setup()
                    .body(user)
            .when()
                    .post("register")
            .then();
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

    public ValidatableResponse updateUser(User user) {
        log.info("Updating an existing user {}", user);

        return setup()
                .body(user)
                .log().all()
        .when()
                .patch("users/2")
        .then()
                .log().all();
    }

}
