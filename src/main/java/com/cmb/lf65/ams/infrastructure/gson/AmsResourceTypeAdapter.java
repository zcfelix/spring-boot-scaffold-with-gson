package com.cmb.lf65.ams.infrastructure.gson;

import com.cmb.lf65.ams.rest.AmsResource;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import org.springframework.hateoas.Link;

import java.lang.reflect.Type;
import java.util.List;

public class AmsResourceTypeAdapter<T> implements JsonSerializer<AmsResource<T>> {

    @Override
    public JsonElement serialize(AmsResource<T> resource, Type type, JsonSerializationContext context) {

        final Class<?> dataClass = resource.getData().getClass();

        final JsonObject dataObject = (JsonObject) context.serialize(resource.getData(), dataClass);
        final List<Link> links = resource.getLinks();
        final Type linkListType = new TypeToken<List<Link>>() {
        }.getType();
        final JsonObject linksObject = (JsonObject) context.serialize(links, linkListType);
        dataObject.add("links", linksObject);

        final JsonObject jsonObject = new JsonObject();
        jsonObject.add("data", dataObject);

        return jsonObject;
    }
}
