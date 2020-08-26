package steps;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import utils.EndPoints;

import static io.restassured.RestAssured.given;

public class PingSteps {
    @Step("Get to /ping/")
    public ValidatableResponse getToPing() {
        return given()
                .when()
                .get(EndPoints.PING)
                .then();
    }
}
