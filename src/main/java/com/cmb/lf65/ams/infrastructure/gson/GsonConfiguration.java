package com.cmb.lf65.ams.infrastructure.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.Link;

import java.lang.reflect.Type;
import java.util.List;

@Configuration
public class GsonConfiguration {

    @Bean
    public Gson gson() {
        final Type linkListType = new TypeToken<List<Link>>() {
        }.getType();

        return new GsonBuilder()
                .registerTypeAdapter(linkListType, new LinksTypeAdapter())
                .create();
    }
}