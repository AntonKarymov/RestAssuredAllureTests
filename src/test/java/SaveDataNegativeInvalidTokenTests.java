import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.http.ContentType;

import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(value = Parameterized.class)
public class SaveDataNegativeInvalidTokenTests extends BaseTest {
    @Parameterized.Parameter(value = 0)
    public String token;
    @Parameterized.Parameter(value = 1)
    public String tokenDescr;
    @Parameterized.Parameter(value = 2)
    public ContentType contentType;

    @Parameterized.Parameters(name = "Post to /api/save_data/ with {1}: {0} and content type: {2}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                //ContentType.JSON
                {"af5dcaa-801f-4a85-9e48-0a79d830fa6b", "old inspired token", ContentType.JSON},
                {"", "empty token", ContentType.JSON},
                {"t", "too short token", ContentType.JSON},
                {"af5dcaa-801f-4a85-9e48-0a79d830fa6b-too-long", "too long token", ContentType.JSON},
                //ContentType.URLENC
                {"af5dcaa-801f-4a85-9e48-0a79d830fa6b", "old inspired token", ContentType.URLENC},
                {"", "empty token", ContentType.URLENC},
                {"t", "too short token", ContentType.URLENC},
                {"af5dcaa-801f-4a85-9e48-0a79d830fa6b-too-long", "too long token", ContentType.URLENC},
        });
    }

    @Epic(value = "Negative /api/save_data/ endpoint")
    @Feature(value = "Save data with incorrect token 403 expected")
    @Test()
    public void saveDataWithIncorrectToken() {
        String correctPayload = "Correct payload";
        ValidatableResponse response = new SaveDataPositiveTests().saveData(contentType, correctPayload, token);
        response.statusCode(403);
    }
}