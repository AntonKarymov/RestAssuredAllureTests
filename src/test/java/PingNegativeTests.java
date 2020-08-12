import io.qameta.allure.Step;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;

import static io.restassured.RestAssured.given;

import java.util.List;

@RunWith(value = Parameterized.class)
public class PingNegativeTests extends BaseTest {
    @Parameterized.Parameter()
    public String url;

    @Parameterized.Parameters(name = "Get to {0} return 404")
    public static List<String> data() {
        return new ArrayList(Arrays.asList("/ping", "/ping/smth", "/ping?a=b", "/ping/?a=b"));
    }

    @Step("Get to url {url} return 404")
    public void notFoundPing(String url) {
        given()
                .when()
                .get(url)
                .then()
                .statusCode(404);
    }

    @Epic(value = "Negative /ping/ endpoint")
    @Feature(value = "GET to ping endpoint with expected 404")
    @Test()
    public void notFoundPing() {
        notFoundPing(url);
    }
}