package utils;

import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import objects.Token;

import static io.restassured.RestAssured.given;
import static utils.Properties.PROPERTIES;

public class RequestHelper {

    public static JsonObject createPayloadJson(String payload) {
        JsonObject postJson = new JsonObject();
        postJson.addProperty("payload", payload);
        return postJson;
    }

    public static String getToken() {
        RestAssured.baseURI = String.format("http://%s:%s", PROPERTIES.getHost(), PROPERTIES.getPort());

        Token token = given()
                .contentType(ContentType.JSON)
                .formParam("username", PROPERTIES.getUsername())
                .formParam("password", PROPERTIES.getPassword())
                .when()
                .post(EndPoints.AUTHORIZE)
                .then()
                .assertThat()
                .statusCode(200)
                .extract().body().as(Token.class);
        return token.getToken();
    }

    //todo: best pratcice delete all created objects after test
    public static void deleteAllUploads() {
        // todo: not implemented yet
    }
}