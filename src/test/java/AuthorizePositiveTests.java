import io.qameta.allure.Epic;
import io.qameta.allure.Description;
import org.junit.Test;
import steps.AuthorizeSteps;

import static org.hamcrest.Matchers.matchesPattern;
import static utils.Properties.PROPERTIES;

public class AuthorizePositiveTests extends BaseTest {
    AuthorizeSteps authorizeSteps = new AuthorizeSteps();

    @Epic(value = "Positive /authorize/ endpoint")
    @Description(value = "Post with correct username/password and check token is correct")
    @Test
    public void checkTokenJson() {
        authorizeSteps.postToAuthorize(PROPERTIES.getUsername(), PROPERTIES.getPassword())
                .statusCode(200)
                .body("token", matchesPattern("[a-z0-9]{8}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{12}"));
        //todo: good practice check json schema using, for example, RestAssured json-schema-validator like:
        //.body(matchesJsonSchemaInClasspath("token.json"))
    }
}