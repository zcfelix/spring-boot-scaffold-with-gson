package com.cmb.lf65.ams.integration;

import com.cmb.lf65.ams.support.ApiTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.io.IOException;

import static com.cmb.lf65.ams.support.CustomMatchers.matchesRegex;
import static com.cmb.lf65.ams.support.TestHelper.readJsonFrom;
import static io.restassured.RestAssured.given;
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
                .get(USERS_URL + "?page=1&size=1")
                .then()
                .statusCode(200)
                .body("data[0].name", notNullValue())
                .body("data[0].sex", matchesRegex("^(MALE|FEMALE)$"))
                .body("data[0].links.self", matchesRegex(".*/users/[0-9]+"))
                .body("pages.links", notNullValue())
                .body("pages.links.self", notNullValue())
                .body("pages.links.first", notNullValue())
                .body("pages.links.last", notNullValue())
                .body("pages.total", notNullValue())
                .body("pages.count", notNullValue())
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
    public void should_return_201_when_create_user_success() throws IOException {
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
