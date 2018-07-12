package com.cmb.lf65.ams.infrastructure.gson;

import com.cmb.lf65.ams.rest.AmsPageResources;
import com.cmb.lf65.ams.rest.AmsResource;
import com.cmb.lf65.ams.rest.Pagination;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.springframework.hateoas.Link;

import java.lang.reflect.Type;
import java.util.List;

public class AmsPageResourcesTypeAdapter<T> implements JsonSerializer<AmsPageResources<T>> {
    @Override
    public JsonElement serialize(AmsPageResources<T> pageResources, Type type, JsonSerializationContext context) {
        final List<AmsResource<T>> resourceList = pageResources.getData();

        final Type linkListType = new TypeToken<List<Link>>(){}.getType();

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
        JsonObject pageElement = (JsonObject) context.serialize(pageResources.getPages(), Pagination.class);
        jsonObject.add("pages", pageElement);

        return jsonObject;
    }
}
