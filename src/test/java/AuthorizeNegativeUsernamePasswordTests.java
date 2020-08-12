import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import utils.EndPoints;

import java.util.Arrays;
import java.util.Collection;

import static io.restassured.RestAssured.given;

@RunWith(value = Parameterized.class)
public class AuthorizeNegativeUsernamePasswordTests extends BaseTest {
    @Parameterized.Parameter(value = 0)
    public String username;
    @Parameterized.Parameter(value = 1)
    public String password;
    @Parameterized.Parameter(value = 2)
    public String description;

    @Parameterized.Parameters(name = "Post to /autorize/ with {2} username: {0}: password: {1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"user", "wrong pass", "incorrect credentials"},
                {"supertest", "", "empty password"},
                {"", "empty payload", "empty username"},
        });
    }

    @Epic(value = "Negative /autorize/ endpoint")
    @Feature(value = "Post with incorrect username/password 403 expected")
    @Test
    public void incorrectAuthorize() {
        incorrectAuthorize(description, username, password);
    }

    @Step("Post to /autorize/ with {0} username: {1}: password: {2}")
    @Test
    public void incorrectAuthorize(String description, String username, String password) {
        given()
                .contentType(ContentType.JSON)
                .formParam("username", username)
                .formParam("password", password)
                .when()
                .post(EndPoints.AUTHORIZE)
                .then()
                .assertThat()
                .statusCode(403);
    }
}