import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import org.junit.After;
import org.junit.BeforeClass;
import utils.RequestHelper;

import static utils.Properties.PROPERTIES;

public class BaseTest {

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = String.format("http://%s:%s", PROPERTIES.getHost(), PROPERTIES.getPort());
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL);
    }

    @After
    public void clean() {
        RequestHelper.deleteAllTokens();
        RequestHelper.deleteAllUploads();
    }
}