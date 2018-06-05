//package lf65.ams.infrastructure;
//
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonSerializationContext;
//import com.google.gson.JsonSerializer;
//import org.springframework.hateoas.Link;
//import org.springframework.hateoas.Resources;
//
//import java.lang.reflect.Type;
//import java.util.Collection;
//import java.util.List;
//
//import static lf65.ams.infrastructure.Converter.linkListType;
//
//public class ResourcesTypeAdapter implements JsonSerializer<Resources> {
//    @Override
//    public JsonElement serialize(Resources resources, Type type, JsonSerializationContext context) {
//        final JsonObject resourcesJson = new JsonObject();
//
//        final Collection data = resources.getContent();
//        final List<Link> links = resources.getLinks();
//
//        resourcesJson.add("data", context.serialize(data));
//        resourcesJson.add("links", context.serialize(links, linkListType));
//
//        return resourcesJson;
//    }
//}
