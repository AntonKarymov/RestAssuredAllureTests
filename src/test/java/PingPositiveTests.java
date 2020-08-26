import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import org.junit.Test;
import steps.PingSteps;

public class PingPositiveTests extends BaseTest {
    //todo implement @Inject
    private PingSteps pingSteps = new PingSteps();

    @Epic(value = "Positive /ping/ endpoint")
    @Description(value = "GET to ping endpoint with expected 200")
    @Test
    public void successPing() {
        pingSteps.getToPing().statusCode(200);
    }
}