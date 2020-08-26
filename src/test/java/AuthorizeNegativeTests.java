import io.qameta.allure.Epic;
import io.qameta.allure.Description;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import steps.AuthorizeSteps;
import steps.CommonSteps;
import steps.SaveDataSteps;

import java.util.HashMap;

public class AuthorizeNegativeTests extends BaseTest {
    SaveDataSteps saveDataSteps = new SaveDataSteps();
    AuthorizeSteps authorizeSteps = new AuthorizeSteps();
    CommonSteps commonSteps = new CommonSteps();

    @Epic(value = "Negative token checks")
    @Description(value = "Check that token expired after 60 sec")
    @Test()
    public void checkTokenExpiration() {
        String token = authorizeSteps.getToken();
        String correctPayload = "Correct payload";
        ValidatableResponse successSave = saveDataSteps.postToSaveData(ContentType.JSON, correctPayload, token);
        successSave.statusCode(200);
        commonSteps.waitInSecond(60);
        ValidatableResponse unsuccessfulSave = saveDataSteps.postToSaveData(ContentType.JSON, correctPayload, token);
        unsuccessfulSave.statusCode(403);
    }

    @Epic(value = "Negative /authorize/ endpoint")
    @Description(value = "Post without username 403 expected")
    @Test
    public void withoutUsername() {
        HashMap<String, String> withoutUsername = new HashMap<>();
        withoutUsername.put("password", "superpassword");
        authorizeSteps.postToAuthorize(withoutUsername)
                .assertThat()
                .statusCode(403);
    }

    @Epic(value = "Negative /authorize/ endpoint")
    @Description(value = "Post without password 403 expected")
    @Test
    public void withoutPassword() {
        HashMap<String, String> withoutPassword = new HashMap<>();
        withoutPassword.put("username", "supertest");
        authorizeSteps.postToAuthorize(withoutPassword)
                .assertThat()
                .statusCode(403);
    }
}