package com.cmb.lf65.ams.support;

import com.cmb.lf65.ams.ExceptionHandlers;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.cmb.lf65.ams.support.GsonConfigurationForUnitTest.gsonHttpMessageConverter;

@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
public abstract class ApiUnitTest {
    public void setUpApi(Object api) {
        RestAssuredMockMvc.standaloneSetup(
                MockMvcBuilders
                        .standaloneSetup(api)
                        .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                        .setMessageConverters(gsonHttpMessageConverter())
                        .setControllerAdvice(new ExceptionHandlers())
        );
    }
}
