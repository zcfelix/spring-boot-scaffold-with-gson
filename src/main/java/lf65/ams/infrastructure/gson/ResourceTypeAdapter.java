//package lf65.ams.infrastructure;
//
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonSerializationContext;
//import com.google.gson.JsonSerializer;
//import org.springframework.hateoas.Link;
//import org.springframework.hateoas.Resource;
//
//import java.lang.reflect.Type;
//import java.util.List;
//
//import static lf65.ams.infrastructure.Converter.linkListType;
//
//public class ResourceTypeAdapter<T> implements JsonSerializer<Resource<T>> {
//
//    @Override
//    public JsonElement serialize(Resource<T> resource, Type type, JsonSerializationContext context) {
//        final JsonObject resourceJson = new JsonObject();
//
//        final T data = resource.getContent();
//        final List<Link> links = resource.getLinks();
//
//        resourceJson.add("data", context.serialize(data));
//        resourceJson.add("links", context.serialize(links, linkListType));
//
//        return resourceJson;
//    }
//}
