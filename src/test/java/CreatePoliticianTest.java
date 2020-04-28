import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.isA;

public class CreatePoliticianTest {


    @Test
    public void createNewPolitician() {
        given().
                contentType("application/json").
                body(buildRequestBody("Test Name", "UK", 1987, "test occupation", 4)).
        when().
            post(APIUtils.POLITICIANS_ENDPOINT).
        then().
                assertThat().
                    statusCode(201).
                    body("ok", is(true)).
                    body("message", is("Entity created successfully!")).
                    body("id", isA(String.class));
    }

    @Test
    public void createShouldFailWhenNoDataIsSent() {
        when().
            post(APIUtils.POLITICIANS_ENDPOINT).
        then().
            assertThat().
            statusCode(400);
    }

    @Test
    public void createShouldFailWhenNameNotIncluded() {
        given().
                contentType("application/json").
                body(buildRequestBody(null, "UK", 1987, "test occupation", 4)).
        when().
                post(APIUtils.POLITICIANS_ENDPOINT).
        then().
                assertThat().
                statusCode(400).
                body("ok", is(false)).
                body("message", is("Invalid request - missing parameters"));
    }

    @Test
    public void createShouldFailWhenCountryNotIncluded() {
        given().
                contentType("application/json").
                body(buildRequestBody("Test Name", null, 1987, "test occupation", 4)).
        when().
                post(APIUtils.POLITICIANS_ENDPOINT).
        then().
                assertThat().
                statusCode(400).
                body("ok", is(false)).
                body("message", is("Invalid request - missing parameters"));
    }

    @Test
    public void createShouldFailWhenYoBNotIncluded() {
        given().
                contentType("application/json").
                body(buildRequestBody("Test Name", "UK", null, "test occupation", 4)).
        when().
                post(APIUtils.POLITICIANS_ENDPOINT).
        then().
                assertThat().
                statusCode(400).
                body("ok", is(false)).
                body("message", is("Invalid request - missing parameters"));
    }

    @Test
    public void createShouldFailWhenPositionNotIncluded() {
        given().
                contentType("application/json").
                body(buildRequestBody("Test Name", "UK", 1987, null, 4)).
        when().
                post(APIUtils.POLITICIANS_ENDPOINT).
        then().
                assertThat().
                statusCode(400).
                body("ok", is(false)).
                body("message", is("Invalid request - missing parameters"));
    }

    @Test
    public void createShouldFailWhenRiskNotIncluded() {
        given().
                contentType("application/json").
                body(buildRequestBody("Test Name", "UK", 1987, "test occupation", null)).
        when().
                post(APIUtils.POLITICIANS_ENDPOINT).
        then().
                assertThat().
                statusCode(400).
                body("ok", is(false)).
                body("message", is("Invalid request - missing parameters"));
    }

    @Test
    public void createShouldFailWhenYoBNotValid() {
        given().
                contentType("application/json").
                body(buildRequestBody("Test Name", "UK", -1, "test occupation", 4)).
        when().
                post(APIUtils.POLITICIANS_ENDPOINT).
                then().
                assertThat().
        statusCode(400).
                body("ok", is(false));
    }

    @Test
    public void createShouldFailWhenRiskOutOfRange() {
        given().
                contentType("application/json").
                body(buildRequestBody("Test Name", "UK", 1987, "test occupation", 0)).
        when().
                post(APIUtils.POLITICIANS_ENDPOINT).
        then().
                assertThat().
                statusCode(400).
                body("ok", is(false)).
                body("message", is("Invalid request - missing parameters"));
    }

    private Map<String, Object> buildRequestBody(String name, String country, Integer yearOfBirth, String position, Integer risk) {
        Map<String, Object> requestBody = new HashMap<String, Object>();
        if (name != null) {
            requestBody.put("name", name);
        }
        if (country != null) {
            requestBody.put("country", country);
        }
        if (yearOfBirth != null) {
            requestBody.put("yob", yearOfBirth);
        }
        if (position != null) {
            requestBody.put("position", position);
        }
        if (risk != null) {
            requestBody.put("risk", risk);
        }
        return requestBody;
    }
}
