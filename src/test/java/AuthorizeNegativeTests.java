import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import utils.EndPoints;
import utils.RequestHelper;

import static io.restassured.RestAssured.given;

public class AuthorizeNegativeTests extends BaseTest {
    @Epic(value = "Negative token checks")
    @Feature(value = "Check that token inspire after 60 sec")
    @Test()
    public void checkTokenInspiration() {
        String token = RequestHelper.getToken();
        String correctPayload = "Correct payload";
        ValidatableResponse successSave = new SaveDataPositiveTests().saveData(ContentType.JSON, correctPayload, token);
        successSave.statusCode(200);
        sleep(60);
        ValidatableResponse unsuccessfulSave = new SaveDataPositiveTests().saveData(ContentType.JSON, correctPayload, token);
        unsuccessfulSave.statusCode(403);
    }

    @Step("Thread sleep {0} seconds")
    public void sleep(long inSecond) {
        try {
            Thread.sleep(inSecond * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Epic(value = "Negative /autorize/ endpoint")
    @Feature(value = "Post without username 403 expected")
    @Step("Sent Post without username")
    @Test
    public void withoutUsername() {
        given()
                .contentType(ContentType.JSON)
                .formParam("password", "superpassword")
                .when()
                .post(EndPoints.AUTHORIZE)
                .then()
                .assertThat()
                .statusCode(403);
    }

    @Epic(value = "Negative /autorize/ endpoint")
    @Feature(value = "Post without password 403 expected")
    @Step("Sent Post without password")
    @Test
    public void withoutPassword() {
        given()
                .contentType(ContentType.JSON)
                .formParam("username", "supertest")
                .when()
                .post(EndPoints.AUTHORIZE)
                .then()
                .assertThat()
                .statusCode(403);
    }
}