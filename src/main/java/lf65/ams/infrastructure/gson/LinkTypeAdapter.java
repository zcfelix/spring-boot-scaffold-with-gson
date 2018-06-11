package lf65.ams.infrastructure.gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.springframework.hateoas.Link;

import java.lang.reflect.Type;

public class LinkTypeAdapter implements JsonSerializer<Link> {

    @Override
    public JsonElement serialize(Link link, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonLink = new JsonObject();
        jsonLink.addProperty("href", link.getHref());
        return jsonLink;
    }
}
