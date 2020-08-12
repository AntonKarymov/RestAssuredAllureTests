import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.http.ContentType;

import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import utils.RequestHelper;

import java.util.Arrays;
import java.util.Collection;

@RunWith(value = Parameterized.class)
public class SaveDataNegativePayload extends BaseTest {
    @Parameterized.Parameter(value = 0)
    public String payload;
    @Parameterized.Parameter(value = 1)
    public String payloadDescr;
    @Parameterized.Parameter(value = 2)
    public ContentType contentType;

    @Parameterized.Parameters(name = "Post to /api/save_data/ with {1}: {0} and content type: {2}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                //todo: clarify requirements for payload
                //ContentType.URLENC
                {"", "empty payload", ContentType.URLENC},
                //ContentType.JSON
                {"", "empty payload", ContentType.JSON},
        });
    }

    @Epic(value = "Negative /api/save_data/ endpoint")
    @Feature(value = "Save data with incorrect payload 400 expected")
    @Test()
    public void saveDataWithIncorrectPayload() {
        ValidatableResponse response = new SaveDataPositiveTests().saveData(contentType, payload, RequestHelper.getToken());
        response.statusCode(400);
    }
}