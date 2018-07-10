package com.cmb.lf65.ams.infrastructure;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

import static java.util.Collections.singletonList;

@Configuration
@EnableWebMvc
public class AmsWebMvcConfiguration implements WebMvcConfigurer {

    private final Gson gson;

    @Autowired
    public AmsWebMvcConfiguration(Gson gson) {
        this.gson = gson;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
        gsonHttpMessageConverter.setGson(gson);
        gsonHttpMessageConverter.setSupportedMediaTypes(singletonList(MediaType.APPLICATION_JSON));
        converters.add(gsonHttpMessageConverter);
    }
}
