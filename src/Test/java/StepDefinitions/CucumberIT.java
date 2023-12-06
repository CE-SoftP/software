package StepDefinitions;

import com.app.SpringJdbcTemplate2OracleApplication;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(Cucumber.class)
@CucumberContextConfiguration
@SpringBootTest(classes = SpringJdbcTemplate2OracleApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "StepDefinitions",
        plugin = {"pretty", "html:target/cucumber-reports"}
)

public class CucumberIT{
    private static int port;
    @Value("${local.server.port}")
    public void setPort(int port) {
        CucumberIT.port = port;
    }

    public static int getPort() {
        return port;
    }
}
