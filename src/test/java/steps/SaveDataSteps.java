package steps;

import com.google.gson.JsonObject;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import utils.EndPoints;

import static io.restassured.RestAssured.given;

public class SaveDataSteps {
    private final String PAYLOAD = "payload";

    @Step("Post to /api/save_data/")
    public ValidatableResponse postToSaveData(ContentType contentType, String payload, String token) {
        RequestSpecification requestSpecification = new RequestSpecBuilder()
                .addHeader("Authorization", String.format("Bearer %s", token))
                .setAccept(ContentType.JSON)
                .build();
        RestAssured.requestSpecification = requestSpecification;

        switch (contentType) {
            case URLENC:
                return given()
                        .contentType(ContentType.URLENC)
                        .accept(ContentType.JSON)
                        .formParam(PAYLOAD, payload)
                        .when()
                        .post(EndPoints.SAVE_DATA)
                        .then();
            case JSON:
                return given()
                        .contentType(ContentType.JSON)
                        .body(createPayloadJson(payload).toString())
                        .accept(ContentType.JSON)
                        .when()
                        .post(EndPoints.SAVE_DATA)
                        .then();
            default:
                throw new IllegalArgumentException("Unsupported Content-type:" + contentType);
        }
    }

    private JsonObject createPayloadJson(String payload) {
        JsonObject postJson = new JsonObject();
        postJson.addProperty(PAYLOAD, payload);
        return postJson;
    }
}
