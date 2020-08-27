import io.qameta.allure.Epic;
import io.qameta.allure.Description;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import steps.AuthorizeSteps;
import steps.SaveDataSteps;
import objects.SaveDataResponse;

import assertions.SaveDataAssert;

public class SaveDataPositiveTests extends BaseTest {
    SaveDataSteps saveDataSteps = new SaveDataSteps();
    AuthorizeSteps authorizeSteps = new AuthorizeSteps();

    @Epic(value = "Positive /api/save_data/ endpoint")
    @Description(value = "Post with correct payload json return saved data id")
    @Test
    public void saveCorrectJsonData() {
        String token = authorizeSteps.getToken();
        ValidatableResponse response = saveDataSteps.postToSaveData(ContentType.JSON, "Correct payload", token);
        response.statusCode(200);
        SaveDataResponse okResponse = response.extract().body().as(SaveDataResponse.class);
        SaveDataAssert.assertThat(okResponse).isSaveDataSuccess("supertest");
    }

    @Epic(value = "Positive /api/save_data/ endpoint")
    @Description(value = "Post with correct payload via x-www-form-urlencoded return saved data id")
    @Test
    public void saveCorrectUrlencodedData() {
        String token = authorizeSteps.getToken();
        ValidatableResponse response = saveDataSteps.postToSaveData(ContentType.URLENC, "Correct payload", token);
        response.statusCode(200);
        SaveDataResponse okResponse = response.extract().body().as(SaveDataResponse.class);
        SaveDataAssert.assertThat(okResponse).isSaveDataSuccess("supertest");
    }
}