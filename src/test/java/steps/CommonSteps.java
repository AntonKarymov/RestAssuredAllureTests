package steps;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CommonSteps {
    @Step("Get to url {url}")
    public ValidatableResponse getToUrl(String url) {
        return given()
                .when()
                .get(url)
                .then();
    }

    @Step("Wait {inSecond} second")
    public void waitInSecond(long inSecond) {
        long now = System.currentTimeMillis();
        long expectedElapsedTime = now + inSecond * 1000;
        while(now < expectedElapsedTime){
            now = System.currentTimeMillis();
        }
    }

    //todo: best pratcice delete all created objects after test
    @Step("Delete all uploads")
    public static void deleteAllUploads() {
        // todo: not implemented yet
    }
}
