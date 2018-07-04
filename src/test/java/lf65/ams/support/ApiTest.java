package lf65.ams.support;

import io.restassured.RestAssured;
import lf65.ams.AmsApplication;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AmsApplication.class,
        webEnvironment = DEFINED_PORT)
@ActiveProfiles(resolver = TestProfileResolver.class)
public abstract class ApiTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiTest.class);

    @Autowired
    private TruncateDatabaseService service;

    @BeforeClass
    public static void beforeClass() {
        LOGGER.info(String.format("Api Tests resolved profiles are [%s].", System.getProperty("spring.profiles.active")));
        RestAssured.port = 8080;
        RestAssured.baseURI = "http://localhost";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @After
    public void tearDown() {
        service.truncate();
    }
}
