import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.Test;
import utils.EndPoints;
import objects.SaveDataResponse;
import utils.RequestHelper;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static matchers.SaveDataResposeMatcher.isSaveDataSuccess;

public class SaveDataPositiveTests extends BaseTest{
    @Epic(value = "Positive /api/save_data/ endpoint")
    @Feature(value = "Post with correct payload json return saved data id")
    @Test
    public void saveCorrectJsonData() {
        ValidatableResponse response = saveData(ContentType.JSON, "Correct payload", RequestHelper.getToken());
        SaveDataResponse okResponse = response.extract().body().as(SaveDataResponse.class);
        assertThat(okResponse, isSaveDataSuccess());
    }

    @Epic(value = "Positive /api/save_data/ endpoint")
    @Feature(value = "Post with correct payload via x-www-form-urlencoded return saved data id")
    @Test
    public void saveCorrectUrlencodedData() {
        ValidatableResponse response = saveData(ContentType.URLENC, "Correct payload", RequestHelper.getToken());
        SaveDataResponse okResponse = response.extract().body().as(SaveDataResponse.class);
        assertThat(okResponse, isSaveDataSuccess());
    }

    @Step("Post to /api/save_data/ with ContentType {0}: payload: {1}, token: {2}")
    public ValidatableResponse saveData(ContentType contentType, String payload, String token) {
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
                        .formParam("payload", payload)
                        .when()
                        .post(EndPoints.SAVE_DATA)
                        .then();
            case JSON:
                return given()
                        .contentType(ContentType.JSON)
                        .body(RequestHelper.createPayloadJson(payload).toString())
                        .accept(ContentType.JSON)
                        .when()
                        .post(EndPoints.SAVE_DATA)
                        .then();
            default:
                throw new IllegalArgumentException("Unsupported Content-type:" + contentType);
        }
    }
}