package com.reqres.tests;

import com.reqres.api.model.User;
import com.reqres.api.services.TestData;
import com.reqres.api.services.UserApiService;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
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

        // Given
        User testUser = testData.SetUserForTests();

        // When
        ValidatableResponse apiResponse = userApiService.createUser(testUser);

        // Then
        apiResponse
                .assertThat().statusCode(201);
    }

    @Test
    void verifyThatItsPossibleToUpdateUser() {

        // Given
        User testUser = testData.SetUserForTests();

        // When
        ValidatableResponse apiResponse = userApiService.updateUser(testUser);

        // Then
        apiResponse
                .assertThat()
                .statusCode(200)
                .body("name", equalTo(testUser.getName()))
                .body("job", equalTo(testUser.getJob()));
    }

    @Test
    void testCanRegisterNewUser() {

        // Given
        User testUser = testData.SetUserForTests();

        // When
        ValidatableResponse apiResponse = userApiService.registerUser(testUser);

        // Then
        apiResponse
                .assertThat()
                .statusCode(201)
                .body("id", not(isEmptyString()));
    }

    @Test
    void TestCanNotifyAboutMissingPassword() {

        // Given
        User testUser = testData.SetUserWithMissedPassword();

        // When
        ValidatableResponse apiRreponce = userApiService.registerUser(testUser);

        // Then
        apiRreponce
                .assertThat()
                .statusCode(400)
                .body("error", equalTo("Missing password"));
    }

    @Test
    void TestCanNotifyAboutMissingEmail() {

        // Given
        User testUser = testData.SetUserWithMissingEmail();

        // When
        ValidatableResponse apiResponse = userApiService.registerUser(testUser);

        // Then
        apiResponse
                .assertThat()
                .statusCode(400)
                .body("error", equalTo("Missing email or username"));
    }
}