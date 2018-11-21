package com.reqres.tests;

import com.reqres.api.User;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.text.IsEmptyString.isEmptyString;

public class GeneralApiTests {
    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://reqres.in/api/";
    }

    @Test
    void pageCanReturnListOfUsers() {
        when().get("users").then().statusCode(200);
    }

    @Test
    void verifyThatUserInformationReturned() {

    }

    @Test
    void verifyThatTestCanCreateNewUser() {

        User testUser = new User()
                .setJob("Plumber")
                .setName("John");

        given()
                .contentType("application/json")
                .body(testUser)
                .log().all()
        .when()
                .post("users")
        .then()
                .log().all()
                .assertThat()
                .statusCode(201)
                .body("id", not(isEmptyString()));
    }

    @Test
    void verifyThatItsPossibleToUpdateUser() {

    }

    @Test
    void verifyThatItsPossibleToRemoveUser() {

    }


}