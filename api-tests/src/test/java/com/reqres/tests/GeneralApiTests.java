package com.reqres.tests;

import com.reqres.api.model.User;
import com.reqres.api.services.TestData;
import com.reqres.api.services.UserApiService;
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

    private UserApiService userApiService = new UserApiService();
    private TestData testData = new TestData();

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

    @Test
    void verifyThatTestCanCreateNewUser() {

        User testUser = testData.SetUserForTests();

        userApiService.createUser(testUser)
                .assertThat().statusCode(201);
    }

    @Test
    void verifyThatItsPossibleToUpdateUser() {

        User testUser = testData.SetUserForTests();

        userApiService.updateUser(testUser)
                .assertThat()
                .statusCode(200)
                .body("name", equalTo(testUser.getName()))
                .body("job", equalTo(testUser.getJob()));
    }

    @Test
    void testCanRegisterNewUser() {

        User testUser = testData.SetUserForTests();

        userApiService.registerUser(testUser)
                .assertThat()
                .statusCode(201)
                .body("id", not(isEmptyString()));
    }

    @Test
    void TestCanNotifyAboutMissingPassword() {

        User testUser = testData.SetUserWithMissedPassword();

        userApiService.registerUser(testUser)
                .assertThat()
                .statusCode(400)
                .body("error", equalTo("Missing password"));
    }

    @Test
    void TestCanNotifyAboutMissingEmail() {
        User testUser = testData.SetUserWithMissingEmail();

        userApiService.registerUser(testUser)
                .assertThat()
                .statusCode(400)
                .body("error", equalTo("Missing email or username"));
    }
}