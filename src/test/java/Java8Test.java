import static org.assertj.core.api.Assertions.assertThat;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import io.dropwizard.Configuration;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Java8Test {

    public static DropwizardAppRule<Configuration> SERVER;

    /**
     * Initialize the DropWizard Server
     */
    @BeforeClass
    public void setup() {
        SERVER = new DropwizardAppRule<>(
                Java8Application.class,
                new Configuration());
        SERVER.getTestSupport().before();
        RestAssured.port = SERVER.getLocalPort();

    }

    @Test
    public void testOptional() throws Exception {

        Response response = RestAssured.given().port(SERVER.getLocalPort()).get("/test");
        assertThat(response.statusCode()).isEqualTo(200);

        String body = response.body().asString();
        System.out.println(body);
        Java8Resource.OptionalThing optionalThing =
                Java8Application.mapper.readValue(body, Java8Resource.OptionalThing.class);
        assertThat(optionalThing.getStuff()).isNotEmpty().contains("stuff");
        assertThat(optionalThing.getNothing()).isEmpty();
        assertThat(optionalThing.getThing()).isPresent();
        assertThat(optionalThing.getThing().get()).contains("thing");
    }
}
