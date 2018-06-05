package lf65.ams.infrastructure.gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.springframework.hateoas.Link;

import java.lang.reflect.Type;
import java.util.List;

public class LinksArrayTypeAdapter implements JsonSerializer<List<Link>> {
    @Override
    public JsonElement serialize(List<Link> links, Type type, JsonSerializationContext context) {

        if (links.size() == 0) {
            return null;
        }

        JsonObject jsonLinks = new JsonObject();
        for (Link link : links) {
            final JsonObject jsonLink = new JsonObject();
            jsonLink.addProperty("href", link.getHref());
            jsonLinks.add(link.getRel(), jsonLink);
        }
        return jsonLinks;
    }
}
