package lf65.ams.infrastructure.gson;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import lf65.ams.domain.Error;

import java.lang.reflect.Type;
import java.util.List;

public class ErrorsTypeAdapter implements JsonSerializer<List<Error>> {
    @Override
    public JsonElement serialize(List<Error> errors, Type type, JsonSerializationContext context) {
        if (errors.size() == 0) {
            return new JsonArray();
        }

        JsonArray jsonErrors = new JsonArray();

        for (Error error : errors) {
            final JsonElement jsonElement = context.serialize(error, Error.class);
            jsonErrors.add(jsonElement);
        }
        return jsonErrors;

    }
}
