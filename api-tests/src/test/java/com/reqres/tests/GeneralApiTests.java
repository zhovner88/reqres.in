package com.reqres.tests;

import com.reqres.api.User;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
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
    void verifyThatSingleUserInformationReturned() {
        given()
                .contentType("application/json")
        .when()
                .get("users/2")
        .then()
                .log().all()
                .statusCode(200)
                .body("id", not(isEmptyString()));
    }

    @Test
    void TestCanHandleNonExistingUser() {
        given()
                .contentType("application/json")
        .when()
                .get("users/23")
        .then()
                .log().all()
                .statusCode(404);
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
        User testUser = new User()
                .setName("John")
                .setJob("Plumber manager");

        given()
                .contentType("application/json")
                .body(testUser)
                .log().all()
        .when()
                .patch("users/2")
        .then()
                .assertThat()
                .statusCode(200)
                .body("name", equalTo(testUser.getName()))
                .body("job", equalTo(testUser.getJob()));
    }

    @Test
    void verifyThatItsPossibleToRemoveUser() {
        given()
                .contentType("application/json")
                .log().all()
        .when()
                .delete("users/2")
        .then()
                .log().all()
                .assertThat()
                .statusCode(204);
    }

    // to-do: randomize username and email fields for each run
    @Test
    void testCanRegisterNewUser() {
        User testUser = new User()
                .setEmail("test@mail.com")
                .setPassword("testPassword");

        given()
                .contentType("application/json")
                .body(testUser)
                .log().all()
        .when()
                .post("register")
        .then()
                .log().all()
                .assertThat()
                .statusCode(201)
                .body("token", not(isEmptyString()));
    }

    @Test
    void TestCanNotifyAboutMissingPassword() {
        User testUser = new User()
                .setEmail("testemail@mail.com");

        given()
                .contentType("application/json")
                .body(testUser)
        .when()
                .post("register")
        .then()
                .log().all()
                .assertThat()
                .statusCode(400)
                .body("error", equalTo("Missing password"));
    }
}