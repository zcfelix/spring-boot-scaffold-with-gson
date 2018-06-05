package lf65.ams.integration;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lf65.ams.support.ApiTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static io.restassured.RestAssured.given;
import static lf65.ams.support.CustomMatchers.matchesRegex;
import static lf65.ams.support.TestHelper.readJsonFrom;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SqlGroup({
        @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/insert-user.sql")
})
public class UsersApiTest extends ApiTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsersApiTest.class);
    private static final String USERS_URL = "/users";


    @Test
    public void should_return_200_when_list_all_users_success() {
        final Response response = given()
                .when()
                .get(USERS_URL)
                .then()
                .statusCode(200)
                .body("data[0].name", notNullValue())
                .body("data[0].sex", matchesRegex("^(MALE|FEMALE)$"))
                .extract()
                .response();
        LOGGER.info(">>>>>{}", response.asString());
    }

    @Test
    public void should_return_200_when_find_user_success() {
        final Response response = given()
                .when()
                .get(USERS_URL + "/1")
                .then()
                .statusCode(200)
                .body("data.name", notNullValue())
                .body("data.sex", matchesRegex("^(MALE|FEMALE)$"))
                .extract()
                .response();
        LOGGER.info(">>>>>{}", response.asString());

    }

    @Test
    public void should_return_201_when_create_user_success() {
        final String content = readJsonFrom("fixture/user/user-201.json");

        final Response response = given()
                .contentType(ContentType.JSON)
                .body(content)
                .when()
                .post(USERS_URL)
                .then()
                .statusCode(201)
                .extract()
                .response();
        assertThat(response.header("Location"), matchesRegex(".*/users/."));
    }
}
