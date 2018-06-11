package lf65.ams.infrastructure.gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.springframework.hateoas.Link;

import java.lang.reflect.Type;
import java.util.List;

import static lf65.ams.infrastructure.Util.isEmpty;

public class LinksArrayTypeAdapter implements JsonSerializer<List<Link>> {

    @Override
    public JsonElement serialize(List<Link> links, Type type, JsonSerializationContext context) {
        if (isEmpty(links)) {
            return null;
        }

        JsonObject jsonLinks = new JsonObject();
        for (Link link : links) {
            if (link != null ) {
                jsonLinks.addProperty(link.getRel(), link.getHref());
            }
        }
        return jsonLinks;
    }
}
