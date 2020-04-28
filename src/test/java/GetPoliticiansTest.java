import com.google.gson.GsonBuilder;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import org.junit.Before;
import org.junit.Test;

import java.time.Year;
import java.util.Date;

import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class GetPoliticiansTest {

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
                get(APIUtils.POLITICIANS_ENDPOINT).
        then().
                assertThat().
                statusCode(200);
    }

    @Test
    public void readShouldReturnFiveResults() {
        when().
                get(APIUtils.POLITICIANS_ENDPOINT).
        then().
                assertThat().
                body("x.size()", is(5));
    }

    @Test
    public void politiciansShouldHaveValidRiskValues() {
        Politician[] politicians = when().
                get(APIUtils.POLITICIANS_ENDPOINT).
                then().
                extract().as(Politician[].class);

        for (Politician politician : politicians) {
            assertThat(
                    "Risk was not between 1 and 5. " + politician,
                    politician.getRisk(),
                    allOf(
                            greaterThanOrEqualTo(1),
                            lessThanOrEqualTo(5)
                    ));
        }
    }

    @Test
    public void politiciansShouldHaveValidYoB() {
        Politician[] politicians = when().
                get(APIUtils.POLITICIANS_ENDPOINT).
                then().
                extract().as(Politician[].class);

        for (Politician politician : politicians) {
            assertThat(
                    "Invalid year of birth. " + politician,
                    politician.getYob(),
                    allOf(
                            greaterThanOrEqualTo(0),
                            lessThanOrEqualTo(Year.now().getValue())));
        }
    }

    @Test
    public void politiciansShouldBeOrderedByCreationDate() {
        Politician[] politicians = when().
                get(APIUtils.POLITICIANS_ENDPOINT).
                then().
                extract().as(Politician[].class);

        Date currentCreationDate = politicians[0].getCreatedAt();

        for (int i = 1; i < politicians.length; i++) {
            Date nextCreationDate = politicians[i].getCreatedAt();
            if (nextCreationDate.compareTo(currentCreationDate) > 0) {
                fail(String.format("Politicians not ordered by creation date. Date %s was greater than %s.", nextCreationDate, currentCreationDate));
            }
            currentCreationDate = nextCreationDate;
        }
    }


}
