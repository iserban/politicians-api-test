import com.google.gson.GsonBuilder;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;

import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GetPoliticiansByIdTest {

    private static final String TEST_ID = "5ea808310f06982d5d7cae90";

    private static ObjectMapperConfig getGsonObjectMapperConfig() {
        return ObjectMapperConfig.objectMapperConfig().gsonObjectMapperFactory((cls, charset) -> new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create());
    }

    @Before
    public void setUp() {
        RestAssured.config = RestAssured.config.objectMapperConfig(getGsonObjectMapperConfig());
    }

    @Test
    public void readShouldReturn200StatusCode() {
        when().
                get(getUrl(TEST_ID)).
        then().
                assertThat().
                statusCode(200);
    }

    @Test
    public void readShouldReturn404StatusCodeForInvalidId() {
        when().
                get(getUrl("123")).
        then().
                assertThat().
                statusCode(404);
    }

    @Test
    public void readShouldReturn404StatusCodeForUnknownId() {
        when().
                get(getUrl("123456789123456789123456")).
        then().
                assertThat().
                statusCode(404);
    }

    @Test
    public void readShouldReturnExpectedPolitician() throws ParseException {
        Politician politician = when().
                get(getUrl(TEST_ID)).
                then().
                extract().as(Politician.class);

        assertThat(politician.getName(), equalTo("Test Name"));
        assertThat(politician.getYob(), equalTo(1987));
        assertThat(politician.getPosition(), equalTo("test occupation"));
        assertThat(politician.getRisk(), equalTo(4));
        assertThat(politician.getCreatedAt(), equalTo(DateFormat.getDateInstance().parse("2020-04-28T10:40:49.597133")));
    }

    private String getUrl(String id) {
        return APIUtils.POLITICIANS_ENDPOINT + "/" + id;
    }

}
