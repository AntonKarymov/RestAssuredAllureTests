import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.http.ContentType;
import org.junit.Test;
import utils.EndPoints;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.matchesPattern;
import static utils.Properties.PROPERTIES;

import io.qameta.allure.Step;

public class AuthorizePositiveTests extends BaseTest {
    @Epic(value = "Positive /autorize/ endpoint")
    @Feature(value = "Post with correct username/password and check token is correct")
    @Test
    public void checkTokenJson() {
        checkTokenJson(PROPERTIES.getUsername(), PROPERTIES.getPassword());
    }

    @Step("Post to /autorize/ with correct username: {0}: password: {1}")
    public void checkTokenJson(String username, String password) {
        given()
                .contentType(ContentType.JSON)
                .formParam("username", username)
                .formParam("password", password)
                .when()
                .post(EndPoints.AUTHORIZE)
                .then()
                .assertThat()
                .statusCode(200)
                .body("token", matchesPattern("[a-z0-9]{8}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{12}"));
        //todo: good practice check json schema using, for example, RestAssured json-schema-validator like:
        //.body(matchesJsonSchemaInClasspath("token.json"))
    }
}