import io.qameta.allure.Epic;
import io.qameta.allure.Description;
import io.restassured.http.ContentType;

import io.restassured.response.ValidatableResponse;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import steps.AuthorizeSteps;
import steps.SaveDataSteps;

@RunWith(value = JUnitParamsRunner.class)
public class SaveDataNegativePayload extends BaseTest {
    SaveDataSteps saveDataSteps = new SaveDataSteps();
    AuthorizeSteps authorizeSteps = new AuthorizeSteps();

    @Epic(value = "Negative /api/save_data/ with ContentType.URLENC")
    @Description(value = "Save data with incorrect payload 400 expected")
    @Test()
    @Parameters({"",
            //todo any incorrect payload except empty?
    })
    public void saveDataWithIncorrectPayloadJson(String payload) {
        ValidatableResponse response = saveDataSteps.postToSaveData(ContentType.JSON, payload, authorizeSteps.getToken());
        response.statusCode(400);
    }

    @Epic(value = "Negative /api/save_data/ endpoint with ContentType.URLENC")
    @Description(value = "Save data with incorrect payload 400 expected")
    @Test()
    @Parameters({"",
            //todo any incorrect payload except empty?
    })
    public void saveDataWithIncorrectPayloadUrlenc(String payload) {
        ValidatableResponse response = saveDataSteps.postToSaveData(ContentType.URLENC, payload, authorizeSteps.getToken());
        response.statusCode(400);
    }
}