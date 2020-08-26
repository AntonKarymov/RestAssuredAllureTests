import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.runner.RunWith;
import steps.CommonSteps;
import org.junit.Test;

@RunWith(value = JUnitParamsRunner.class)
public class PingNegativeTests extends BaseTest {
    private CommonSteps commonSteps = new CommonSteps();

    @Epic(value = "Negative /ping/ endpoint")
    @Description(value = "GET to ping endpoint with expected 404")
    @Test
    @Parameters({
            "/ping",
            "/ping/smth",
            "/ping?a=b",
            "/ping/?a=b"
    })
    public void notFoundPing(String url) {
        commonSteps.getToUrl(url);
    }
}