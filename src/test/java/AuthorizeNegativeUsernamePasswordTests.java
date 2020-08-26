import io.qameta.allure.Epic;
import io.qameta.allure.Description;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import steps.AuthorizeSteps;

@RunWith(value = JUnitParamsRunner.class)
public class AuthorizeNegativeUsernamePasswordTests extends BaseTest {
    AuthorizeSteps authorizeSteps = new AuthorizeSteps();

    @Epic(value = "Negative /authorize/ endpoint")
    @Description(value = "Post with incorrect username/password 403 expected")
    @Test()
    @Parameters({
            "user, wrong pass",
            "supertest, ",
            ", superpassword",
    })
    public void incorrectAuthorize(String username, String password) {
        authorizeSteps.postToAuthorize(username, password).statusCode(403);
    }
}