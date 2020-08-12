import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import org.junit.Test;
import utils.EndPoints;

import static io.restassured.RestAssured.given;

public class PingPositiveTests extends BaseTest {
    @Epic(value = "Positive /ping/ endpoint")
    @Feature(value = "GET to ping endpoint with expected 200")
    @Test
    @Step("Get to /ping/")
    public void successPing() {
        given()
                .when()
                .get(EndPoints.PING)
                .then()
                .statusCode(200);
    }
}