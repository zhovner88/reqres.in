package com.reqres.api;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.text.IsEmptyString.isEmptyString;

public class UserApiService {

    public void registerUser(User user) {
        given()
                .contentType("application/json")
                .log().all()
                .body(user)
        .when()
                .post("register")
        .then()
                .log().all()
                .assertThat()
                .statusCode(201)
                .body("token", not(isEmptyString()));
    }

    public void createUser(User user) {
        given()
                .contentType("application/json")
                .body(user)
                .log().all()
        .when()
                .post("users")
        .then()
                .log().all()
                .assertThat()
                .statusCode(201)
                .body("id", not(isEmptyString()));
    }

    public void createUserWithError(User user) {
        given()
                .contentType("application/json")
                .body(user)
                .when()
                .post("register")
                .then()
                .log().all()
                .assertThat()
                .statusCode(400)
                .body("error", equalTo("Missing password"));
    }

}
