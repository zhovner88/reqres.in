import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.when;

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
    void verifyThatItsPossibleToCreateNewUser() {

    }

    @Test
    void verifyThatItsPossibleToUpdateUser() {

    }

    @Test
    void verifyThatItsPossibleToRemoveUser() {

    }


}