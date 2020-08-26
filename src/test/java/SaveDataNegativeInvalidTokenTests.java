import io.qameta.allure.Epic;
import io.qameta.allure.Description;
import io.restassured.http.ContentType;

import io.restassured.response.ValidatableResponse;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import steps.SaveDataSteps;

@RunWith(value = JUnitParamsRunner.class)
public class SaveDataNegativeInvalidTokenTests extends BaseTest {
    SaveDataSteps saveDataSteps = new SaveDataSteps();

    @Epic(value = "Negative /api/save_data/ endpoint with ContentType.JSON")
    @Description(value = "Save data with incorrect token 403 expected")
    @Test()
    @Parameters({
            //empty token
            "",
            //old inspired token
            "af5dcaa-801f-4a85-9e48-0a79d830fa6b",
            //too short token
            "t",
            //too long token
            "af5dcaa-801f-4a85-9e48-0a79d830fa6b-too-long"
    })
    public void saveDataWithIncorrectTokenJson(String token) {
        String correctPayload = "Correct payload";
        ValidatableResponse response = saveDataSteps.postToSaveData(ContentType.JSON, correctPayload, token);
        response.statusCode(403);
    }

    @Epic(value = "Negative /api/save_data/ endpoint with ContentType.URLENC")
    @Description(value = "Save data with incorrect token 403 expected")
    @Test()
    @Parameters({
            //empty token
            "",
            //old inspired token
            "af5dcaa-801f-4a85-9e48-0a79d830fa6b",
            //too short token
            "t",
            //too long token
            "af5dcaa-801f-4a85-9e48-0a79d830fa6b-too-long"
    })
    public void saveDataWithIncorrectTokenUrlenc(String token) {
        String correctPayload = "Correct payload";
        ValidatableResponse response = saveDataSteps.postToSaveData(ContentType.URLENC, correctPayload, token);
        response.statusCode(403);
    }
}