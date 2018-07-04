package lf65.ams.rest.api;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import lf65.ams.domain.user.UserRepository;
import lf65.ams.support.ApiUnitTest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static lf65.ams.support.CustomMatchers.matchesRegex;
import static lf65.ams.support.TestHelper.readJsonFrom;
import static lf65.ams.support.TestHelper.readStringFrom;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class UsersApiUnitTest extends ApiUnitTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsersApiUnitTest.class);
    private static final String USERS_URL = "/users";


    @Mock
    private UserRepository repository;

    @InjectMocks
    private UsersApi usersApi;

    @Before
    public void setUp() {
        setUpApi(usersApi);
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
    }

    @Test
    public void should_return_415_when_create_user_with_invalid_json() throws IOException {
        final String content = readStringFrom("fixture/invalid-json.json");

        final MockMvcResponse response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(content)
                .post(USERS_URL)
                .then()
                .statusCode(415)
                .body("size()", is(1))
                .body("[0].status", is("415"))
                .body("[0].title", is("不支持的媒体类型"))
                .extract()
                .response();
        LOGGER.info(">>>>>{}", response.asString());
    }

    @Test
    public void should_400_when_create_user_without_required_field() throws IOException {
        final String content = readJsonFrom("fixture/user/user-400-without-required-field.json");

        final MockMvcResponse response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(content)
                .when()
                .post(USERS_URL)
                .then()
                .statusCode(400)
                .body("size()", is(4))
                .body("[0].status", is("400"))
                .body("[0].title", matchesRegex("必选字段.*不存在"))
                .extract()
                .response();
        LOGGER.info(">>>>>{}", response.asString());
    }

    @Test
    public void should_return_400_when_create_user_with_invalid_age() throws IOException {
        final String content = readJsonFrom("fixture/user/user-400-with-invalid-age.json");

        final MockMvcResponse response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(content)
                .when()
                .post(USERS_URL)
                .then()
                .statusCode(400)
                .body("size()", is(1))
                .body("[0].status", is("400"))
                .body("[0].title", matchesRegex("字段.*的取值.*不合法"))
                .extract()
                .response();
        LOGGER.info(">>>>>{}", response.asString());
    }

    @Test
    public void should_return_404_when_user_not_found() {
        final MockMvcResponse response = given()
                .accept(ContentType.JSON)
                .when()
                .get(USERS_URL + "/999")
                .then()
                .statusCode(404)
                .body("size()", is(1))
                .body("[0].status", is("404"))
                .body("[0].title", is("用户不存在"))
                .extract()
                .response();
        LOGGER.info(">>>>>{}", response.asString());
    }
}
