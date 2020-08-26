package steps;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import objects.Token;
import utils.EndPoints;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static utils.Properties.PROPERTIES;

public class AuthorizeSteps {
    private final String USERNAME = "username";
    private static String PASSWORD = "password";

    @Step("Post to /authorize/ with params")
    public ValidatableResponse postToAuthorize(Map<String, ?> formParams) {
        return given()
                .contentType(ContentType.JSON)
                .formParams(formParams)
                .when()
                .post(EndPoints.AUTHORIZE)
                .then();
    }

    @Step("Post to /authorize/ with username {username} and password {password}")
    public ValidatableResponse postToAuthorize(String username, String password) {
        HashMap<String, String> usernamePassword = new HashMap<>();
        usernamePassword.put(USERNAME, username);
        usernamePassword.put(PASSWORD, password);
        return postToAuthorize(usernamePassword);
    }

    @Step("Get new token")
    public String getToken() {
        RestAssured.baseURI = String.format("http://%s:%s", PROPERTIES.getHost(), PROPERTIES.getPort());

        HashMap<String, String> usernamePassword = new HashMap<>();
        usernamePassword.put(USERNAME, PROPERTIES.getUsername());
        usernamePassword.put(PASSWORD, PROPERTIES.getPassword());

        Token token = postToAuthorize(usernamePassword)
                .statusCode(200)
                .extract().body().as(Token.class);
        return token.getToken();
    }
}
