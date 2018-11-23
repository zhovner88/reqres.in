package com.reqres.tests;

import com.reqres.api.User;
import com.reqres.api.UserApiService;
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

    UserApiService userApiService = new UserApiService();

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

        userApiService.createUser(testUser)
                .assertThat().statusCode(201);
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
    void verifyThatItsPossibleToDeleteUser() {
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

        userApiService.registerUser(testUser)
                .assertThat()
                .statusCode(201)
                .body("id", not(isEmptyString()));
    }

    @Test
    void TestCanNotifyAboutMissingPassword() {
        User testUser = new User()
                .setEmail("testemail@mail.com");

        userApiService.registerUser(testUser)
                .assertThat()
                .statusCode(400)
                .body("error", equalTo("Missing password"));
    }
}