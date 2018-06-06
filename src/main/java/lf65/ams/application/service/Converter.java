package lf65.ams.application.service;

import com.google.gson.Gson;

import java.util.Map;

public final class Converter {
    private Converter() {
    }

    private static final Gson GSON = new Gson();

    public static <T> T toDomain(Map<String, Object> map, Class<T> clazz) {
        return GSON.fromJson(GSON.toJson(map), clazz);
    }
}
