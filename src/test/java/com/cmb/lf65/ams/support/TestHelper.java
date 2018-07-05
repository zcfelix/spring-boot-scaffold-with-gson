package com.cmb.lf65.ams.support;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public final class TestHelper {

    private static final String ENCODING = "UTF-8";

    private TestHelper() {
    }

    public static String readJsonFrom(String fileName) throws IOException {
        String content = readStringFrom(fileName);
        return new Gson().fromJson(content, JsonObject.class).toString();
    }

    public static String readStringFrom(String fileName) throws IOException {
        return FileUtils.readFileToString(getFile(fileName), ENCODING);
    }

    private static File getFile(String fileName) {
        final URL url = TestHelper.class.getClassLoader().getResource(fileName);
        assert url != null;
        return new File(url.getFile());
    }
}
