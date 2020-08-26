import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import org.junit.After;
import org.junit.BeforeClass;

import static utils.Properties.PROPERTIES;

public class BaseTest {

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = String.format("http://%s:%s", PROPERTIES.getHost(), PROPERTIES.getPort());
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL);
        RestAssured.requestSpecification = new RequestSpecBuilder().build().filter(new AllureRestAssured());
    }

    @After
    public void clean() {
        RestAssured.requestSpecification = null;
        //deleteAllUploads();
    }
}