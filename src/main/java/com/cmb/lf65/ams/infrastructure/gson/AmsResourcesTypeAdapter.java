package com.cmb.lf65.ams.infrastructure.gson;

import com.cmb.lf65.ams.rest.AmsResource;
import com.cmb.lf65.ams.rest.AmsResources;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.springframework.hateoas.Link;

import java.lang.reflect.Type;
import java.util.List;

public class AmsResourcesTypeAdapter<T> implements JsonSerializer<AmsResources<T>> {
    @Override
    public JsonElement serialize(AmsResources<T> resources, Type type, JsonSerializationContext context) {
        final List<AmsResource<T>> resourceList = resources.getResources();

        final Type linkListType = new TypeToken<List<Link>>() {
        }.getType();
        final JsonArray resourceJsonArray = new JsonArray();

        resourceList.forEach(x -> {
            final Class<?> dataClass = x.getData().getClass();
            final JsonObject linkElement = (JsonObject) context.serialize(x.getLinks(), linkListType);
            final JsonObject contentElement = (JsonObject) context.serialize(x.getData(), dataClass);
            contentElement.add("links", linkElement);
            resourceJsonArray.add(contentElement);
        });

        JsonObject jsonObject = new JsonObject();
        jsonObject.add("data", resourceJsonArray);

        final JsonElement resourcesLink = context.serialize(resources.getLinks(), linkListType);
        jsonObject.add("links", resourcesLink);

        return jsonObject;
    }
}
